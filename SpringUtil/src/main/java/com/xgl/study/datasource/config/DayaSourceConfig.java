package com.xgl.study.datasource.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.xgl.study.datasource.constants.DataSourceConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
class DataSourceConfig {
	
	@Bean(DataSourceConstants.DB_01)
	@ConfigurationProperties(prefix="spring.datasource.datasource01")
	public DataSource db01Datasource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(DataSourceConstants.DB_02)
	@ConfigurationProperties(prefix="spring.datasource.datasource02")
	public DataSource db02Datasource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	public DataSource defaultDataSource() {
		DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
		dynamicDataSource.addDataSource(DataSourceConstants.DB_01, db01Datasource());
		dynamicDataSource.addDataSource(DataSourceConstants.DB_02, db02Datasource());
		dynamicDataSource.setPrimary("DataSourceConstants.DB_01");		
		return dynamicDataSource;
	}

}
