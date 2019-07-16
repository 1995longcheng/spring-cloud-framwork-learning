package com.learning.nacos.consumer;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(NacosConsumerApplication.class, args);
	}
	
	/**
	 * 添加负载均衡器
	 * @return
	 */
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@RestController
	public class Hi {
		
		@Autowired
		private RestTemplate restTemplate;
		
		@GetMapping("hi")
		public String hi() {
			return restTemplate.getForObject("http://spring-cloud-nacos-provider/hello", String.class);
		}
	}
}
