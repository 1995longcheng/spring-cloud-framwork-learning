package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
public class ComsumerFeignApplication {
	
	/**
	 * Eureka+Feign+Hystrix
	 * 
	 * 1、启动eureka服务器
	 * 2、启动服务提供者，microservice-provider
	 * 3、开启EnableCircuitBreaker注解
	 * 4、配置feign:
  				hystrix:
    				enabled: true
	 * 5、编写FeignClient接口，注意：需要给 value属性，fallback属性
	 * 6、编写FallBack类，注意:需要声明为Spring容器类
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ComsumerFeignApplication.class);
	}
}
