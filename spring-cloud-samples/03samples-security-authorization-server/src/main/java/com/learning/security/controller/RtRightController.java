package com.learning.security.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.learning.common.BaseController;
import com.learning.common.enums.ResponseData;
import com.learning.security.entity.RtRight;
import com.learning.security.service.RtRightService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api("安全模块-权限管理中心")
@RestController
public class RtRightController extends BaseController {
	@Autowired
	private RtRightService rightService;
	
	@ApiOperation(value = "通过权限ID查询权限详情接口")
	@ApiImplicitParam(name = "rightid", value = "权限ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/right/getRightById")
	public Object getRightById(HttpServletRequest request) {
		try {
			Long rightid = Long.valueOf(request.getParameter("rightid"));
			
			RtRight right = rightService.getRightById(rightid);
			return ResponseData.ok(right);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "通过权限名称查询权限详情接口")
	@ApiImplicitParam(name = "rightname", value = "权限名称", defaultValue = "", dataType = "String", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/right/getRightByName")
	public Object getRightByName(HttpServletRequest request) {
		try {
			String rightname = request.getParameter("rightname");
			
			RtRight right = rightService.getRightByName(rightname);
			return ResponseData.ok(right);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "分页查询权限详情接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pagenum", value = "页号", defaultValue = "", dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "pagesize", value = "单页容量", defaultValue = "", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/right/getRightPageList")
	public Object getRightPageList(HttpServletRequest request) {
		try {
			Integer pagenum = Integer.valueOf(request.getParameter("pagenum"));
			Integer pagesize = Integer.valueOf(request.getParameter("pagesize"));
			
			Page<RtRight> rightList = rightService.getRightPageList(pagenum,pagesize);
			return ResponseData.ok(rightList);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "新增权限接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rightid", value = "权限ID", defaultValue = "", dataType = "Long", paramType = "query"),
		@ApiImplicitParam(name = "rightname", value = "权限名称", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "righttype", value = "权限类型", defaultValue = "1", dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "url", value = "微服务URL", defaultValue = "", dataType = "String", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/right/addRight")
	public Object addRight(HttpServletRequest request) {
		
		try {
			RtRight right = new RtRight();
			right.setRightid(Long.valueOf(request.getParameter("rightid")));
			right.setRightname(request.getParameter("rightname"));
			right.setRighttype(request.getParameter("righttype"));
			right.setUrl(request.getParameter("url"));
			right.setInserttime(Long.valueOf(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")));
			rightService.addRight(right);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "更新权限接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rightid", value = "权限ID", defaultValue = "", dataType = "Long", paramType = "query"),
		@ApiImplicitParam(name = "rightname", value = "权限名称", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "righttype", value = "权限类型", defaultValue = "1", dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "url", value = "微服务URL", defaultValue = "", dataType = "String", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/right/uptRight")
	public Object uptRight(HttpServletRequest request) {
		try {
			RtRight right = new RtRight();
			right.setRightid(Long.valueOf(request.getParameter("rightid")));
			right.setRightname(request.getParameter("rightname"));
			right.setRighttype(request.getParameter("righttype"));
			right.setUrl(request.getParameter("url"));
			
			rightService.uptRight(right);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "通过权限ID删除权限接口")
	@ApiImplicitParam(name = "rightid", value = "权限ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/right/delRightById")
	public Object delRightById(HttpServletRequest request) {

		try {
			Long rightid = Long.valueOf(request.getParameter("rightid"));
			
			rightService.delRightById(rightid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
}
