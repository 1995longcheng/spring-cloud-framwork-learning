package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

import com.learning.configuration.DefaultRibbonConfiguration;
import com.learning.configuration.ExcludeFromComponentScan;


/**
 * Ribbon的相关配置存在优先级：
 * 1、application.yml配置
 * 2、使用@Bean注解标注
 * 3、
 * @author xiongchaoqun
 * @date 2018年8月29日
 */
@SpringBootApplication
@RibbonClient(name="learning-spring-cloud-microservice-provider",configuration=DefaultRibbonConfiguration.class)
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromComponentScan.class)})
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
