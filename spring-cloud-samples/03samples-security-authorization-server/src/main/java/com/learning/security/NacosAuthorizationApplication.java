package com.learning.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 开启权限验证服务器
 * 
 * @author xiongchaoqun
 * @date 2019年7月9日
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
public class NacosAuthorizationApplication {
	public static void main(String[] args) {
		SpringApplication.run(NacosAuthorizationApplication.class, args);
	}
}