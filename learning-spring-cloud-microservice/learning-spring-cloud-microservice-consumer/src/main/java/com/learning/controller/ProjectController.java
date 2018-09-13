package com.learning.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.learning.entity.Project;

@RestController
@RibbonClient(name="learning-spring-cloud-microservice-provider")
public class ProjectController {
	@Autowired
	private RestTemplate restTemplate;

	// http://localhost:10001/getProject?project_id=100001
	@GetMapping(value = "/getProject")
	public String getProject(@RequestParam String project_id) {
		/**
		 * getForEntity带参数请求两种方式-1
		 */
		Map<String, String> map = new HashMap<>();
		map.put("id", project_id);
		String json = this.restTemplate.getForEntity("http://localhost:9001/getProject?id={id}", String.class,map).getBody();
		return json;
	}
	
	// http://localhost:10001/getProject1?project_id=100001
	@GetMapping(value = "/getProject1")
	public String getProject1(@RequestParam String project_id) {
		/**
		 * getForEntity带参数请求两种方式-2
		 */
		String json = this.restTemplate.getForEntity("http://localhost:9001/getProject?id={1}", String.class,project_id).getBody();
		return json;
	}
}
