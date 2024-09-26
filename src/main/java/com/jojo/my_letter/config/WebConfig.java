package com.jojo.my_letter.config;

import com.jojo.my_letter.controller.service.AccessLogService;
import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.filter.AccessLogFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AccessLogService accessLogService;
	private final LoginService loginService;

	@Bean
	public FilterRegistrationBean filterBean() {
		AccessLogFilter accessLogFilter = new AccessLogFilter(accessLogService, loginService);
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean(accessLogFilter);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");

		// 외부 디렉토리 경로 추가
		registry
				.addResourceHandler("/resources/images/upload/**")
				.addResourceLocations("file:///C:/newsletterProject_images/upload/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}
}
