package com.learning.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.learning.common.BaseController;
import com.learning.common.enums.ResponseData;
import com.learning.security.entity.RtRight;
import com.learning.security.entity.RtRole;
import com.learning.security.entity.RtUser;
import com.learning.security.service.RtRightService;
import com.learning.security.service.RtRoleService;
import com.learning.security.service.RtUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api("安全模块-用户管理中心")
@RestController
public class RtUserController extends BaseController {

	@Autowired
	private RtUserService userService;
	
	@Autowired
	private RtRoleService roleService;
	
	@Autowired
	private RtRightService rightService;
	
	@ApiOperation(value = "通过用户ID查询用户详情接口")
	@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/getUserById")
	public Object getUserById(HttpServletRequest request) {
		try {
			Long userid = Long.valueOf(request.getParameter("userid"));
			
			RtUser user = userService.getUserById(userid);
			List<RtRole> roles = new ArrayList<RtRole>();
			user.setRoles(roles);
			
			List<Long> roleIdList = userService.getRoleIdsByUserId(userid);
			for (Long roleid : roleIdList) {
				RtRole role = roleService.getRoleById(roleid);
				List<RtRight> rights = new ArrayList<RtRight>();
				role.setRights(rights);
				List<Long> rightIdList = roleService.getRightIdsById(role.getRoleid());
				for (Long rightid : rightIdList) {
					RtRight right = rightService.getRightById(rightid);
					rights.add(right);
				}
				roles.add(role);
			}
			return ResponseData.ok(user);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "通过用户名称查询用户详情接口")
	@ApiImplicitParam(name = "username", value = "用户名称", defaultValue = "", dataType = "String", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/getUserByName")
	public Object getUserByName(HttpServletRequest request) {
		try {
			String username = request.getParameter("username");
			
			RtUser user = userService.getUserByName(username);
			List<RtRole> roles = new ArrayList<RtRole>();
			user.setRoles(roles);

			List<Long> roleIdList = userService.getRoleIdsByUserId(user.getUserid());
			for (Long roleid : roleIdList) {
				RtRole role = roleService.getRoleById(roleid);
				List<RtRight> rights = new ArrayList<RtRight>();
				role.setRights(rights);
				List<Long> rightIdList = roleService.getRightIdsById(role.getRoleid());
				for (Long rightid : rightIdList) {
					RtRight right = rightService.getRightById(rightid);
					rights.add(right);
				}
				roles.add(role);
			}
			return ResponseData.ok(user);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "分页查询用户详情接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pagenum", value = "页号", defaultValue = "", dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "pagesize", value = "单页容量", defaultValue = "", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/getUserPageList")
	public Object getUserPageList(HttpServletRequest request) {
		try {
			Integer pagenum = Integer.valueOf(request.getParameter("pagenum"));
			Integer pagesize = Integer.valueOf(request.getParameter("pagesize"));
			
			Page<RtUser> userList = userService.getUserPageList(pagenum,pagesize);
			
			for (RtUser user : userList) {
				List<RtRole> roles = new ArrayList<RtRole>();
				user.setRoles(roles);
				List<Long> roleIdList = userService.getRoleIdsByUserId(user.getUserid());
				for (Long roleid : roleIdList) {
					RtRole role = roleService.getRoleById(roleid);
					List<RtRight> rights = new ArrayList<RtRight>();
					role.setRights(rights);
					List<Long> rightIdList = roleService.getRightIdsById(role.getRoleid());
					for (Long rightid : rightIdList) {
						RtRight right = rightService.getRightById(rightid);
						rights.add(right);
					}
					roles.add(role);
				}
			}
			return ResponseData.ok(userList);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "新增用户接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "Long", paramType = "query"),
		@ApiImplicitParam(name = "username", value = "用户名称", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "enabled", value = "是否可用", defaultValue = "1", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/addUser")
	public Object addUser(HttpServletRequest request) {
		try {
			RtUser user = new RtUser();
			user.setUserid(Long.valueOf(request.getParameter("userid")));
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setEnabled(Integer.valueOf(request.getParameter("enabled")));
			userService.addUser(user);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "更新用户接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "Long", paramType = "query"),
		@ApiImplicitParam(name = "username", value = "用户名称", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "enabled", value = "是否可用", defaultValue = "1", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/uptUser")
	public Object uptUser(HttpServletRequest request) {
		try {
			RtUser user = new RtUser();
			user.setUserid(Long.valueOf(request.getParameter("userid")));
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setEnabled(Integer.valueOf(request.getParameter("enabled")));
			userService.uptUser(user);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	
	@ApiOperation(value = "通过用户ID删除用户接口")
	@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/delUserById")
	public Object delUserById(HttpServletRequest request) {
		try {
			Long userid = Long.valueOf(request.getParameter("userid"));
			
			userService.delUserById(userid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "通过用户ID查询用户所属角色接口")
	@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/getRoleIdsByUserId")
	public Object getRoleIdsByUserId(HttpServletRequest request) {
		try {
			Long userid = Long.valueOf(request.getParameter("userid"));
			
			List<Long> ids = userService.getRoleIdsByUserId(userid);
			return ResponseData.ok(ids);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "新增用户-角色接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "String", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/addUserReRole")
	public Object addUserReRole(HttpServletRequest request) {
		try {
			Long userid = Long.valueOf(request.getParameter("userid"));
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			
			userService.addUserReRole(userid, roleid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "删除用户-角色接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "用户ID", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "String", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/user/delUserReRole")
	public Object delUserReRole(HttpServletRequest request) {
		try {
			Long userid = Long.valueOf(request.getParameter("userid"));
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			
			userService.delUserReRole(userid, roleid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
}
