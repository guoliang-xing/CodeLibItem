package com.xgl.study.datasource.aspect;

import com.xgl.study.datasource.DynamicDataSourceContextHolder;
import com.xgl.study.datasource.annotion.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Aspect
public class DataSourceAspect  {
	private final static Logger logger =  LoggerFactory.getLogger(DataSourceAspect.class);
	
	@Pointcut(value = "@annotation(com.xgl.study.datasource.annotion.DataSource)")
	private void pointCut() {}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		String dsKey = getDataSourceAnnotation(joinPoint).value();
		try{
			DynamicDataSourceContextHolder.setContextKey(dsKey);
			return joinPoint.proceed();
		}finally {
			DynamicDataSourceContextHolder.removeContextKey();
		}
	}

	/**
     * 获取注册
	 * @param joinPoint
     * @return
     */
	private DataSource getDataSourceAnnotation(ProceedingJoinPoint joinPoint){
		Class<?> targetClass = joinPoint.getTarget().getClass();
		DataSource annotation = targetClass.getAnnotation(DataSource.class);
		if(Objects.nonNull(annotation)){
			return annotation;
		}else {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			return signature.getMethod().getAnnotation(DataSource.class);
		}
	}

}
