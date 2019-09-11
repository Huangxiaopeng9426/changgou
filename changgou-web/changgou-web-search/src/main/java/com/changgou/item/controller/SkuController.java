package com.changgou.item.controller;

import com.changgou.entity.Page;
import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {
   @Autowired
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map<String,String> searchMap, Model model){
        //调用changgou-service-search的微服务
        Map<String,Object> resultMap = skuFeign.search(searchMap);
        //搜索的结果
        model.addAttribute("result",resultMap);
        //搜索的条件
        model.addAttribute("searchMap",searchMap);
        //请求的地址
        String url = url(searchMap);
        model.addAttribute("url",url);

        //分页的条件
        Page<SkuInfo> page = new Page<SkuInfo>(
                Long.valueOf(resultMap.get("total").toString()),
                Integer.valueOf(resultMap.get("pageNum").toString()),
                Integer.valueOf(resultMap.get("pageSize").toString()));
        model.addAttribute("page",page);
        return "search";
    }

    private String url(Map<String, String> searchMap) {
        String url = "/search/list";
        if (searchMap!=null) {
            url+="?";
            for (Map.Entry<String, String> stringStringEntry : searchMap.entrySet()) {
                String key = stringStringEntry.getKey();
                url+=key+"="+stringStringEntry.getValue()+"&";
                if (key.equals("pageNum")) {
                    continue;
                }
            }
            //去掉最后一个&
            url=url.substring(0,url.length()-1);
        }
        return url;
    }
}
