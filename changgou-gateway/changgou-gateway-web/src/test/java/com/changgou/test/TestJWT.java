package com.changgou.test;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestJWT {
    @Test
    public void createJwt(){
        long currentTimeMillis = System.currentTimeMillis();
        long l = currentTimeMillis + 200000;
        //生成令牌
        //构建jwtbuiler
        JwtBuilder builder = Jwts.builder();
        //1.设置头(不设置.默认就有)
        //2.设置载荷
        builder.setId("唯一的标识")
                .setIssuer("颁发者")//办法令牌的人
                .setSubject("主题")//描述jwt的信息
                .setExpiration(new Date(l))//设置过期时间
            .signWith(SignatureAlgorithm.HS256,"itcast");//签名

        Map<String, Object> map = new HashMap<>();
        map.put("myaddres","cn");
        map.put("mycity","sz");
        builder.addClaims(map);
        String compact = builder.compact();//令牌的生成
        System.out.println(compact);
    }

    @Test
    public void parseJwt(){
        //解析令牌
       // String st = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgifQ.AU33UoJ8Vz_ZoCtKcvCEm5R0UFknLE-06E49z1h0nfI";
       // String st ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgiLCJteWNpdHkiOiJzeiIsIm15YWRkcmVzIjoiY24ifQ.uQm1mPNrYqhJuhxr7OAv-VFprMrrGo3EezmoyrrQxOo";
        String st = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgiLCJleHAiOjE1NjcyMjExODAsIm15Y2l0eSI6InN6IiwibXlhZGRyZXMiOiJjbiJ9.fNeXYJgHzTfrOoeQUW6BmMwp6PCbqFe1d8vHA6_PHD0";
        //解析前,先获取秘钥
        JwtParser itcast = Jwts.parser().setSigningKey("itcast");
        //获取信息
        Claims body = itcast.parseClaimsJws(st).getBody();
        System.out.println(body);
    }

}
