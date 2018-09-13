package com.learning.configuration;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages="com.learning.repository",sqlSessionTemplateRef="sqlSessionTemplate")
public class DruidConfiguration {
	
	/**
	 * 定义数据源1
	 * @return
	 */
	@Bean(name="druidDataSource")
	@ConfigurationProperties(prefix="spring.datasource.druid")
	public DataSource initDruidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}
	
	@Bean("dataSourceTransactionManager")
	public DataSourceTransactionManager initDataSourceTransactionManager(@Qualifier(value="druidDataSource") DataSource  dataSource) {
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource);
		return manager;
	}
	
	
	@Bean(name="sqlSessionFactoryBean")
	public SqlSessionFactoryBean initSqlSessionFactoryBean(@Qualifier(value="druidDataSource") DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		try {
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate initSqlSessionTemplate(@Qualifier(value="sqlSessionFactoryBean") SqlSessionFactoryBean bean) {
		SqlSessionTemplate template = null;
		try {
			template = new SqlSessionTemplate(bean.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return template;
	}
}
