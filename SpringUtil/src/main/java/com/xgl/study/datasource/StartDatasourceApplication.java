package com.xgl.study.datasource;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy  //AOP自动代理
public class StartDatasourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartDatasourceApplication.class);
    }
}
