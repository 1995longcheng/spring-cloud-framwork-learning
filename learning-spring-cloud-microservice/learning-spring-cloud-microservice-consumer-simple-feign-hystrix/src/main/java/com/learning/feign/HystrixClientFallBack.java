package com.learning.feign;

import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallBack implements EmployeeFeignClient{

	@Override
	public String getProject(String id) {
		// TODO Auto-generated method stub
		return "request error hystrix fallback";
	}

	@Override
	public String getEmployee(String id) {
		// TODO Auto-generated method stub
		return "request error hystrix fallback";
	}

}
