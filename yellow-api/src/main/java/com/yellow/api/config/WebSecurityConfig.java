package com.yellow.api.config;

import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.api.security.JwtAuthenticationTokenFilter;
import com.yellow.api.service.SysLogService;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResponseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.time.Duration;
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
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private SysLogService sysLogService;

    @Resource
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        // 自动创建表,第一次执行会创建，以后要执行就要删除掉！
//        jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                // 跨域
                cors().configurationSource(corsConfigurationSource()).and()
                // 添加基本header
                .headers().addHeaderWriter(getResponseCors()).frameOptions().disable().and()
                // 禁用session
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // 禁用表单登录
                .formLogin().disable()
                // 禁用匿名登录
                .anonymous().disable()
                // 登录认证
                .rememberMe().tokenRepository(persistentTokenRepository()).and()

                // 接口授权
                .authorizeHttpRequests().
                // 匿名路径
                antMatchers(SystemProperties.auth.getAnon().toArray(new String[]{})).permitAll().
                // 所有请求都需认证
                anyRequest().authenticated().and()

                // 异常处理
                .exceptionHandling().
                // 认证的异常处理
                authenticationEntryPoint((request, response, e) -> ResponseResult.output(response, CommonCode.UNAUTHENTICATED)).
                // 授权的异常处理
                accessDeniedHandler((request, response, e) -> ResponseResult.output(response, CommonCode.UNAUTHORISE)).

                and().httpBasic(Customizer.withDefaults());

        // 授权过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
                new Header("X-Frame-Options","ALLOWALL"),
                //使ajax请求能够取到header中的jwt token信息
                new Header("Access-Control-Expose-Headers","Authorization"),
                new Header("Content-Type","application/json; charset=utf-8"))
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
