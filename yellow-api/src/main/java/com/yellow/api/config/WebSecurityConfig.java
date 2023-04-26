package com.yellow.api.config;

import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.api.security.JwtAuthenticationLoginFilter;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResponseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

/**
 * 权限资源配置
 * @author zhouhao
 * @date  2021/3/24 14:14
 *
 * @EnableGlobalMethodSecurity  激活接口上的PreAuthorize注解
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
                // 跨域
                cors().and().headers().addHeaderWriter(this.getResponseCors()).
                // 禁用session
                and().csrf().disable().
                // 请求认证
                authorizeRequests().
                    // 匿名路径
                    antMatchers(SystemProperties.auth.getAnon().toArray(new String[]{})).permitAll().
                    // 所有请求都需认证
                    anyRequest().authenticated().
                and()./*addFilterBefore(jwtAuthenticationLoginFilter, UsernamePasswordAuthenticationFilter.class).*/
                formLogin().loginProcessingUrl("/auth/login").
                and().rememberMe().key("yellow").
                // 异常处理
                and().exceptionHandling().
                    // 认证的异常处理
                    authenticationEntryPoint((request, response, e) -> ResponseResult.output(response, CommonCode.UNAUTHENTICATED)).
                    // 授权的异常处理
                    accessDeniedHandler((request, response, e) -> ResponseResult.output(response, CommonCode.UNAUTHORISE)).
                // token鉴权 过滤器
                // 启用http 基础验证
                and().httpBasic().and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.
                getSharedObject(AuthenticationManagerBuilder.class).
                userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder()).
                and().build();
    }

    // 采用bcrypt对密码进行编码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 响应头添加跨域
     * @return org.springframework.security.web.header.writers.StaticHeadersWriter
     * @author zhouhao
     * @date  2021/3/29 15:13
     */
    private StaticHeadersWriter getResponseCors(){
        return new StaticHeadersWriter(Arrays.asList(
                //支持所有源的访问
                new Header("Access-control-Allow-Origin","*"),
                //使ajax请求能够取到header中的jwt token信息
                new Header("Access-Control-Expose-Headers","Authorization"),
                new Header("Content-Type","application/json; charset=utf-8"))
        );
    }

    // 跨域配置
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
