package com.ljh.jhoj.config;

import com.ljh.jhoj.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebApplicationConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor userInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截，但是放行所有静态资源
        registry.addInterceptor(userInterceptor()).excludePathPatterns("/css/**", "/js/**", "/fonts/**", "/img/**", "/plugin/**");
    }
}
