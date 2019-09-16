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
import com.learning.security.service.RtRightService;
import com.learning.security.service.RtRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 角色端点
 * @author xiongchaoqun
 * @date 2019年9月9日
 */
@Api("安全模块-角色管理中心")
@RestController
public class RtRoleController extends BaseController {
	
	@Autowired
	private RtRoleService roleService;
	
	@Autowired
	private RtRightService rightService;
	
	@ApiOperation(value = "通过角色ID查询角色详情接口")
	@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/getRoleById")
	public Object getRoleById(HttpServletRequest request) {
		try {
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			
			RtRole role = roleService.getRoleById(roleid);			
			List<RtRight> rights = new ArrayList<RtRight>();
			role.setRights(rights);

			List<Long> rightIdList = roleService.getRightIdsById(role.getRoleid());
			for (Long rightid : rightIdList) {
				RtRight right = rightService.getRightById(rightid);
				rights.add(right);
			}
			return ResponseData.ok(role);

		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "通过角色名称查询角色详情接口")
	@ApiImplicitParam(name = "rolename", value = "角色名称", defaultValue = "", dataType = "String", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/getRoleByName")
	public Object getRoleByName(HttpServletRequest request) {
		try {
			String rolename = request.getParameter("rolename");
			
			RtRole role = roleService.getRoleByName(rolename);		
			List<RtRight> rights = new ArrayList<RtRight>();
			role.setRights(rights);

			List<Long> rightIdList = roleService.getRightIdsById(role.getRoleid());
			for (Long rightid : rightIdList) {
				RtRight right = rightService.getRightById(rightid);
				rights.add(right);
			}
			return ResponseData.ok(role);

		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "分页查询角色详情接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pagenum", value = "页号", defaultValue = "", dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "pagesize", value = "单页容量", defaultValue = "", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/getRolePageList")
	public Object getRolePageList(HttpServletRequest request) {
		try {
			Integer pagenum = Integer.valueOf(request.getParameter("pagenum"));
			Integer pagesize = Integer.valueOf(request.getParameter("pagesize"));
			
			Page<RtRole> roleList = roleService.getRolePageList(pagenum, pagesize);
			for (RtRole role : roleList) {
				List<RtRight> rights = new ArrayList<RtRight>();
				role.setRights(rights);

				List<Long> rightIdList = roleService.getRightIdsById(role.getRoleid());
				for (Long rightid : rightIdList) {
					RtRight right = rightService.getRightById(rightid);
					rights.add(right);
				}
			}
			return ResponseData.ok(roleList);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "新增角色接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "Long", paramType = "query"),
		@ApiImplicitParam(name = "rolename", value = "角色名称", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "enabled", value = "是否可用", defaultValue = "1", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/addRole")
	public Object addRole(HttpServletRequest request) {
		try {
			RtRole role = new RtRole();
			role.setRoleid(Long.valueOf(request.getParameter("roleid")));
			role.setRolename(request.getParameter("rolename"));
			role.setEnabled(Integer.valueOf(request.getParameter("enabled")));
			
			roleService.addRole(role);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "更新角色接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "Long", paramType = "query"),
		@ApiImplicitParam(name = "rolename", value = "角色名称", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "enabled", value = "是否可用", defaultValue = "1", dataType = "Integer", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/uptRole")
	public Object uptRole(HttpServletRequest request) {
		try {
			RtRole role = new RtRole();
			role.setRoleid(Long.valueOf(request.getParameter("roleid")));
			role.setRolename(request.getParameter("rolename"));
			role.setEnabled(Integer.valueOf(request.getParameter("enabled")));
			
			roleService.uptRole(role);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	
	@ApiOperation(value = "通过角色ID删除角色接口")
	@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/delRoleById")
	public Object delRoleById(HttpServletRequest request) {
		try {
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			roleService.delRoleById(roleid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "通过角色ID查询角色所属权限接口")
	@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "Long", paramType = "query")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/getRightIdsById")
	public Object getRightIdsById(HttpServletRequest request) {
		try {
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			List<Long> ids = roleService.getRightIdsById(roleid);
			return ResponseData.ok(ids);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "新增角色-权限接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "rightid", value = "权限ID", defaultValue = "", dataType = "String", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/addRoleReRight")
	public Object addRoleReRight(HttpServletRequest request) {
		try {
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			Long rightid = Long.valueOf(request.getParameter("rightid"));
			
			roleService.addRoleReRight(roleid, rightid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
	
	@ApiOperation(value = "删除角色-权限接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleid", value = "角色ID", defaultValue = "", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "rightid", value = "角色ID", defaultValue = "", dataType = "String", paramType = "query")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/role/delRoleReRight")
	public Object delRoleReRight(HttpServletRequest request) {
		try {
			Long roleid = Long.valueOf(request.getParameter("roleid"));
			Long rightid = Long.valueOf(request.getParameter("rightid"));
			
			roleService.delRoleReRight(roleid, rightid);
			return ResponseData.ok("接口调用成功！");
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
	}
}
