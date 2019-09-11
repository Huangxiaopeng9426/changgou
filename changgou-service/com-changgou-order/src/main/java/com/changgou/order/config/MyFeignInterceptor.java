package com.changgou.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class MyFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //使用RequestContextHolder工具获取request相关变量
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes!=null) {
            //1.获取请求的对象
            HttpServletRequest request = attributes.getRequest();
            //2.取出所有头的文件信息的key  是从网关传来的数据
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames!=null) {
                while (headerNames.hasMoreElements()){
                    //头的名称
                    String name = headerNames.nextElement();
                    //头名称对应的值
                    String value = request.getHeader(name);
                    //3.将头信息传递给fegin (restTemplate)
                    requestTemplate.header(name,value);
                }
            }
        }

    }
}
