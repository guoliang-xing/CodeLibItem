package com.xgl.test.spring;

import com.xgl.test.spring.interceptor.InterceptorDemo1;
import com.xgl.test.spring.interceptor.InterceptorDemo2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 添加拦截器
 */
@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Resource(name="interceptorDemo1")
    private InterceptorDemo1 interceptorDemo1;

    @Resource(name="interceptorDemo2")
    private InterceptorDemo2 interceptorDemo2;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorDemo1).addPathPatterns("/**").order(1);
        registry.addInterceptor(interceptorDemo2).addPathPatterns("/**").order(2);
    }
}
