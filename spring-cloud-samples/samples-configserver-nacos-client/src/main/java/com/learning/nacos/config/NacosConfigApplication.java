package com.learning.nacos.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * 注意：
 * 1、必须使用bootstrap.yaml配置nacos-server
 * 2、元数据data-id必须使用spring.application.name.后缀【yaml、properties】
 * 
 * @author xiongchaoqun
 * @date 2019年7月2日
 */
@SpringBootApplication
public class NacosConfigApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosConfigApplication.class, args);
		String userName = applicationContext.getEnvironment().getProperty("user.name");
		String userAge = applicationContext.getEnvironment().getProperty("user.age");
		System.err.println("user name :" + userName + "; age: " + userAge);
	}
}
