package com.learning.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.learning.common.BaseApplication;

@EnableResourceServer
@SpringBootApplication
@ComponentScan(basePackages = "com.learning")
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class NacosProviderWithSecurityApplication extends BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosProviderWithSecurityApplication.class, args);
	}
}
