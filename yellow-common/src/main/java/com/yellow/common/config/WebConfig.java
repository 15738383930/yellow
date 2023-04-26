package com.yellow.common.config;

import com.yellow.common.interceptor.SqlInjectInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**注册拦截器
		 * */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Resource
	private SqlInjectInterceptor sqlInjectInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sqlInjectInterceptor).addPathPatterns("/**");
	}
}
