package com.xgl.study.aop;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xgl.study.annotion.DataSource;

@Component
@Aspect
public class DataSourceAspect  {
	private final static Logger logger =  LoggerFactory.getLogger(DataSourceAspect.class);
	
	@Pointcut(value = "@annotation(com.xgl.study.annotion.DataSource)")
	private void pointCut() {}
	
	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		String dataSourceName = getAnnotation(joinPoint).value();
		logger.info(dataSourceName);
		return joinPoint.proceed();
	}
	
	private DataSource getAnnotation(ProceedingJoinPoint joinPoint) {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		//获取类注解
		DataSource ds = targetClass.getAnnotation(DataSource.class);
		if(Objects.nonNull(ds)) {
			return ds;
		}else {
			//获取方法上的注解
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            return signature.getMethod().getAnnotation(DataSource.class);
		}
	}

}
