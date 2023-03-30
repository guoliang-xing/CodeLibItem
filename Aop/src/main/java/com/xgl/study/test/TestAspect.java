package com.xgl.study.test;

import org.springframework.stereotype.Component;

import com.xgl.study.annotion.DataSource;


@Component
public class TestAspect {
	
	@DataSource("ceshi1******************")
	public void  test() {
		 System.out.println("执行业务处理...");
	}

}
