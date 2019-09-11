package com.changgou.user.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TokenDecode {

    private static final String PUBLIC_KEY = "public.key";

    //获取令牌
    public String getToken(){
        OAuth2AuthenticationDetails authentication = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String tokenValue = authentication.getTokenValue();
        return tokenValue;
    }

    /**
     * 获取用户的信息 主要是获取登录的用户名
     * @return
     */
    public Map<String,String> getUserInfo(){
        //获取令牌
        String token = getToken();

        //解析令牌  获取公钥
        String pubKey = getPubKey();
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(pubKey));

        String claims = jwt.getClaims();//获取载荷数据

        System.out.println(claims);

        //返回 将json转map数据

        Map<String,String> map = JSON.parseObject(claims, Map.class);
        return map;
    }


    /**
     * 获取公钥的方法
     * @return
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }
}
