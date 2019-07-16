package com.learning.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.nacos.configuration.FamilyConfiguration;
import com.learning.nacos.configuration.FamilyConfiguration.Person;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("公司操作接口")
@RestController
@RequestMapping("/family")
public class FamilyController {

	@Autowired
	private FamilyConfiguration cfg;

	@ApiOperation(value = "获取父亲对象方法")
	@GetMapping("/getFather")
	public Person getFather() {
		return cfg.getFather();
	}
}
