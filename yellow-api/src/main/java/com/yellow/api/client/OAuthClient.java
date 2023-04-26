package com.yellow.api.client;

import com.yellow.api.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 服务器使用情况客户端
 * @author Hao.
 * @version 1.0
 * @since 2022/10/12 8:32
 */
@FeignClient(name = "oAuthClient", url = "http://10.114.9.48:8080/api", configuration = FeignConfig.class)
public interface OAuthClient {

}
