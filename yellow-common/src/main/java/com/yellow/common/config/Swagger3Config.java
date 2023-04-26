package com.yellow.common.config;

import com.yellow.common.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

/**
 * swagger3x配置
 * @author zhouhao
 * @date  2021/3/22 15:47
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {

    @Bean
    public Docket createRestApi(Environment environment) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //通过environment.acceptProfiles判断是否处在自己设定的环境当中
                .enable(environment.acceptsProfiles(Profiles.of("dev","test")))
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.cdwm.mrp.controller")) 指定扫描包
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()

                // 支持的通讯协议集合
                .protocols(newHashSet("https", "http"))

                // 授权信息设置，必要的header token等认证信息
//                .securitySchemes(securitySchemes())

                // 授权信息全局应用
//                .securityContexts(securityContexts())

                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("API文档")
                .version("1.0")
                .build();
    }



    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey(Constants.TOKEN_HEADER, Constants.TOKEN_HEADER, In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference(Constants.TOKEN_HEADER, new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }
}
