package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.learning.feign.EmployeeFeignClient;

@RestController
public class ProjectController {
	
	@Autowired
	private EmployeeFeignClient feignClient;

	//http://localhost:10001/getProject?id=100001
	@RequestMapping(value = "/getProject",method=RequestMethod.GET)
	public String getProject(@RequestParam String id) {
		String json = this.feignClient.getProject(id);
		return json;
	}
	//http://localhost:10001/getEmployee/100001
	@RequestMapping(value = "/getEmployee/{id}",method=RequestMethod.GET)
	public String getEmployee(@PathVariable String id) {
		String json = this.feignClient.getEmployee(id);
		return json;
	}
}
