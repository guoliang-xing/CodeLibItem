package com.xgl.study;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.xgl.study.test.TestAspect;

public class AspectTest {
	    public static void main(String[] args) {
	        // 通过Java配置来实例化Spring容器
	        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AspectConfig.class);
	 
	        // 在Spring容器中获取Bean对象
	        TestAspect testAspect = context.getBean(TestAspect.class);
	        testAspect.test();
	 
	        // 销毁该容器
	        context.destroy();;
	    }

}
