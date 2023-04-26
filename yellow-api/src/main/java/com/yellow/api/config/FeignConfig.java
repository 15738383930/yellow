package com.yellow.api.config;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * Feign配置<br>
 *      忽略ssl证书
 * @author Hao.
 * @version 1.0
 * @since 2022/10/12 9:53
 */
@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            return new Client.Default(ctx.getSocketFactory(), (hostname, session) -> true);
        } catch (Exception e) {
            return null;
        }
    }
}
