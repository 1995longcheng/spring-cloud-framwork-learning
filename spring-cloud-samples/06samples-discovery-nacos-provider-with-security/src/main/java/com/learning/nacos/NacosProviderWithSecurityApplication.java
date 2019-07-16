package com.learning.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
@ComponentScan(basePackages = "com.learning")
@EnableDiscoveryClient
public class NacosProviderWithSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosProviderWithSecurityApplication.class, args);
	}
}