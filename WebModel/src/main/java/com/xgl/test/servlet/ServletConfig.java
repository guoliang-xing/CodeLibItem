package com.xgl.test.servlet;

import com.xgl.test.servlet.filter.FilterDemo;
import com.xgl.test.servlet.listener.ListenerDemo;
import com.xgl.test.servlet.servlet.ServletDemo;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

/**
 *  Servlet三大工具的java配置
 *  使用注解时，对应的注解为@WebServlet，@WebFilter，@WebListener
 *  使用时，启动类需要加 @ServletComponentScan
 *
 */
@Configuration
public class ServletConfig {

    /**
     * 注册servlet
     * 对应注解 @WebServlet
     */
    @Bean
    public ServletRegistrationBean getServlet(ServletDemo servletDemo){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(servletDemo);
        registrationBean.addUrlMappings("/servlet/servlet");
        return registrationBean;
    }

    /**
     * 注册过滤器
     * 对应注解 @WebFilter
     * @param filterDemo  过滤器实现类
     * @return filter 注册器
     */
    @Bean
    public FilterRegistrationBean getFilter(FilterDemo filterDemo){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filterDemo);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        return registrationBean;
    }

    /**
     * 注册监听器
     * 对应注解 @WebListener
     * @param listenerDemo
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean getListener(ListenerDemo listenerDemo){
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        registrationBean.setListener(listenerDemo);
        return registrationBean;
    }
}
