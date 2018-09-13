package com.learning.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用FeignClient注解来绑定服务提供者所提供服务
 * 1、实现RestTemplate功能
 * 2、实现ILoadBalance功能
 * @author xiongchaoqun
 * @date 2018年9月5日
 */
@FeignClient(name="learning-spring-cloud-microservice-provider-simple",fallback=HystrixClientFallBack.class)
public interface EmployeeFeignClient {
	
	/**
	 * 这里使用@RequestParam/@PathVariale都必须添加value属性
	 */
	@GetMapping(value="/getProject")
	public String getProject(@RequestParam("id") String id); 
	
	@GetMapping(value="/getEmployee/{id}")
	public String getEmployee(@PathVariable("id") String id); 
	
}
