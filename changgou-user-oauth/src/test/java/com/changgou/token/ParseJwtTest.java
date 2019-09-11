package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.gcGSA0hO2myFzfaEUYe_gtVz2RJMmDRExM-wjALJhvv2zOfwkC8CgHtT1FkCIs-aKn-ApKyKB9Kbw6m_jpdsUhqBdtYK2fW1SDKjqiKUqZHNZVEm_TFG_FYHbob-TpdBpT5nmDVHWXBqoEyA9It6ZJnUvWKIlkTmK7WGlW3sFtoW2r9hljRt278n2-DxVFM2TBluvUXeubAjarseDl4te8RpzFX85aemiARm5ogOeFW9c-6GP8b7dYh_WTWd9iLOrc5er-TFUYmHG84q_31-8aQx5cYhF1Y8O2ZURUP9jIHDl7L8B0Jq2h_khDQSQo_BZ2tb_Ve7WhvY2CH22jSclw";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg6HqJxSEgUdw7pGBRoY8Tnp/dEdXWuWy+muzypP57w2geyZA2QLZXy1nhQXF72WdSbLZDgzWs4s733I08f5PpzbBm+7Un+nBF5dmOWKnBHzlw+v5YGOagM7OLAcfXtbAjwBvae3OucUIEkmWrpGXNHDDNxTlZ9hwYcBjRv3WMR8kokdxM0SMxdLTQgEUGsA4kF1sHo5IQvMlys3OnW1CIwkPyZdBSQ/zB4Sw7fCtu3r2L/AEbD3yplozdlVTRDQRYaZT4DsACf7+2047HYPutocNc4AhnN1O382racMLaX41S51P1ca238JJ5PRFRVJjlo6Ms2ZqdGZ5SDUl14wA8QIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
