package com.learning.nacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learning.common.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("公司操作接口")
@RestController
public class CompanyController extends BaseController {

	@ApiOperation(value = "获取公司名称方法")
	@RequestMapping(value = "/company/getName", method = { RequestMethod.POST, RequestMethod.GET })
	public String getName() {
		String companyName = "cattsoft";
		return companyName;
	}
}
