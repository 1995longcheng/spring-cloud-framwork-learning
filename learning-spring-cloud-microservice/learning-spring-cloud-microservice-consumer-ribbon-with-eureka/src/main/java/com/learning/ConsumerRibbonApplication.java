package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;


/**
 * Ribbon的相关配置存在优先级：
 * 1、application.yml配置
 * 2、使用@Bean注解标注
 * @author xiongchaoqun
 * @date 2018年8月29日
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerRibbonApplication {
	/**
	 * 添加负载均衡能力RestTemplate @LoadBalance 注解
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate initRestTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConsumerRibbonApplication.class);
	}

}
