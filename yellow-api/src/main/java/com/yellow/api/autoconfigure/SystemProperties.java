package com.yellow.api.autoconfigure;

import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 外部配置属性
 * @author zhou
 * @since  2021/6/16 11:14
 */
@Component
public class SystemProperties {

    public static Auth auth;

    public static Key key;

    public static PrivateKey PRIVATE_KEY;

    public static String PUBLIC_KEY;

    private static final ResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    public SystemProperties(Auth auth, Key key){
        SystemProperties.auth = auth;
        SystemProperties.key = key;
    }

    @Data
    @Component
    @ConfigurationProperties("yellow.auth")
    public static class Auth {

        /** 用户信息存储到redis的过期时间(秒) */
        private long userValiditySeconds = 3600L;

        /** cookie信息存活时间(秒) */
        private int cookieMaxAge = -1;

        /** 签发cookie的服务器ip/域名。本地的话，用localhost */
        private String cookieDomain = "zhou.com";

        /** 鉴权：匿名的路径 */
        private List<String> anon = new ArrayList<>();
    }

    @Data
    @Component
    @ConfigurationProperties("yellow.key")
    public static class Key {

        /** 私钥存放位置（一般存resources下：xx.keystore） */
        private String privateKeyLocation;

        /** 公钥钥存放位置（一般存resources下：publickey.txt） */
        private String publicKeyLocation;

        /** 秘钥库密码 */
        private String password;

        /** 别名 */
        private String alias;

        /** 秘钥密码 */
        private String secret;
    }

    @PostConstruct
    public void initKey() throws IOException {
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(SystemProperties.key.getPrivateKeyLocation()), SystemProperties.key.getPassword().toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(SystemProperties.key.getAlias(), SystemProperties.key.getSecret().toCharArray());

        PRIVATE_KEY = keyPair.getPrivate();

        PUBLIC_KEY = IOUtils.toString(RESOURCE_RESOLVER.getResource(SystemProperties.key.getPublicKeyLocation()).getURI(), StandardCharsets.UTF_8);
    }
}
