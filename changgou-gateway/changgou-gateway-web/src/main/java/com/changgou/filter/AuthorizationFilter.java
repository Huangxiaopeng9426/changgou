package com.changgou.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements GlobalFilter , Ordered {
    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    private static final String USER_LOGIN_URL = "http://localhost:9001/oauth/login";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请的对象
        ServerHttpRequest request = exchange.getRequest();
        //2.获取效应对象
        ServerHttpResponse response = exchange.getResponse();
        //3.判断 是否是登录的url 是 方行
        if (request.getURI().getPath().startsWith("/api/user/login") ||request.getURI().getPath().startsWith("/api/user/add")) {
            return chain.filter(exchange);
        }
        //4.判断 是否是登录的url 不是 进行权限校验
        //4.1 从头获取令牌
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        if (StringUtils.isEmpty(token)) {
            //4.2 从cookie中获取令牌
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (cookie!=null) {
                token = cookie.getValue();
            }

        }

        //4.3 从参数中获取令牌
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        //4.4 以上都没有获取到令牌,结束请求
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().set("Location",USER_LOGIN_URL+"?Form="+request.getURI().toString());
           return response.setComplete();
        }

        //5. 获取到了令牌 ,解析令牌

        try {
            //Claims claims = JwtUtil.parseJWT(token);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //添加头信息 传递给 各个微服务()
        request.mutate().header(AUTHORIZE_TOKEN,"Bearer "+token);
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
