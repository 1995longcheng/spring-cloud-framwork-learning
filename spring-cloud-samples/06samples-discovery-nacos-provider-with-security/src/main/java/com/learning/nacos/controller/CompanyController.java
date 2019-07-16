package com.learning.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("公司操作接口")
@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@ApiOperation(value="获取公司名称方法")
	@GetMapping("/getName")
	public String getName() {
		String companyName = "cattsoft";
		return companyName;
	}
}
