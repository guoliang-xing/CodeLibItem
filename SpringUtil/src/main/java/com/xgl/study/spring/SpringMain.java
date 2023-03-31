package com.xgl.study.spring;

import com.xgl.study.spring.config.AppConfig;
import com.xgl.study.spring.service.UserService;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

import java.util.Set;

public class SpringMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean(UserService.class);
        userService.test();

        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(AppConfig.class);
        for(String am :abd.getMetadata().getAnnotationTypes()){
            System.out.println(am);

        }

    }
}
