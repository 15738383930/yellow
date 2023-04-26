package com.yellow.api;

import com.alibaba.fastjson.JSON;
import com.yellow.api.security.JwtTokenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {

    //创建jwt令牌
    @Test
    public void testCreateJwt(){
        //密钥库文件
        String keystore = "zh.keystore";
        //密钥库的密码
        String keystore_password = "zhoukeystore";

        //密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource(keystore);
        //密钥别名
        String alias  = "zhkey";
        //密钥的访问密码
        String key_password = "zhouhao";
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_password.toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_password.toCharArray());
        //获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        //jwt令牌的内容
        Map<String,String> body = new HashMap<>();
        body.put("name","itcast");
        String bodyString = JSON.toJSONString(body);
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(bodyString, new RsaSigner(aPrivate));
        //生成jwt令牌编码
        String encoded = jwt.getEncoded();
        System.out.println(JSON.toJSONString(jwt));

    }

    //校验jwt令牌
    @Test
    public void testVerify(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----1IIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm5pf3IgFo3DncyM5ub1r41NNEpj1VZwORASgo7QgN+7qYIhfvE/lFcXEnxu8ZT8usIugsvDbtocxPsiAIwIxHhs+VF/9bSbrBSGvYVlpdrwI6tIwG/Vjd1IDQGcxqaGNnGoQe1N1W+3mN0bV+tAsberop9bZYREFXoBc6skMU7D8AHqUIqRNpOjGzrMtcAgfpzwApeQeOF426AyTpsk1EaSr77lwFCP524oYK5yxKOPrnKrXTAcXMv+tH1cuQDGqsRFtiwhAUHsC9LEEuEKGP9Lz7JiILQc5/nWw8DEUS/3WfzIlAjwV17TaU7EGUJD2VEwiFVvtEb2BF8i9tyqAEQIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpbnZhbGlkVGltZSI6MTY4MjMzMTcxNzE3OCwidXNlcm5hbWUiOiJ0ZXN0In0.ZE_SWL1yRkVYmhgyMJaB3Tm3pBSS3E2KcSEmZpXMkYrxC_wnh1vHtKOJHcqRG8fikqTlrmJMfiVQCvmXnCTkBEwPkW4GDsKLNaBJPd1UKpswMgcXcO7mn9_OmSA22haSs_CAh7G7PKjDXcdm2KDzzWG_IOMpQxgnwSxMjTbl1_Me8MSvQxI4a9jzoQVjHy62ZDdJ0qXVJD1wH1wanSeOlxJYxWSfpKrTz0_QgXFX4mshdxjwB6xf6V3y20QUbGvpWtdjm5y4RpKXHRs4gHOYlqjps1AYMPVUpRxz-UgBR_lcPfro0CYHvSN1YRZnbr7shW3PvTtoQAzOh8HmVkeLGQ";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }

    //校验jwt令牌
    @Test
    public void testtest(){
        final String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpbnZhbGlkVGltZSI6MTY4MjMzMTcxNzE3OCwidXNlcm5hbWUiOiJ0ZXN0In0.ZE_SWL1yRkVYmhgyMJaB3Tm3pBSS3E2KcSEmZpXMkYrxC_wnh1vHtKOJHcqRG8fikqTlrmJMfiVQCvmXnCTkBEwPkW4GDsKLNaBJPd1UKpswMgcXcO7mn9_OmSA22haSs_CAh7G7PKjDXcdm2KDzzWG_IOMpQxgnwSxMjTbl1_Me8MSvQxI4a9jzoQVjHy62ZDdJ0qXVJD1wH1wanSeOlxJYxWSfpKrTz0_QgXFX4mshdxjwB6xf6V3y20QUbGvpWtdjm5y4RpKXHRs4gHOYlqjps1AYMPVUpRxz-UgBR_lcPfro0CYHvSN1YRZnbr7shW3PvTtoQAzOh8HmVkeLGQ";
        final String username = JwtTokenUtils.getUsernameFromToken(token);
        System.out.println(username);
    }
}
