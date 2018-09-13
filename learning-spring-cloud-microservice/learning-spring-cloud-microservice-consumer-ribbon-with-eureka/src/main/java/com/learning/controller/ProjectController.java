package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProjectController {
	
	  @Autowired
	  private LoadBalancerClient loadBalancerClient;
	  
	  @Autowired
	  private RestTemplate restTemplate;
	  //http://localhost:10001/getProject/1
	  @GetMapping(value="/getProject/{id}")
	  public String getProject(@PathVariable String id) {
		  String json = "";
		  ServiceInstance instance = loadBalancerClient.choose("learning-spring-cloud-microservice-provider");
		  System.out.println(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
		  return json;
	  }
	  
}
