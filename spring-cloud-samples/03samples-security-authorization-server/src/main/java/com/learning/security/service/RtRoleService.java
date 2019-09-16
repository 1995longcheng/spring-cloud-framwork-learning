package com.learning.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.learning.security.entity.RtRole;
import com.learning.security.mapper.RtRoleMapper;

@Service
public class RtRoleService {
	private static final Logger LOG = LoggerFactory.getLogger(RtRoleService.class);
	
	@Autowired
	private RtRoleMapper roleMapper;

	public RtRole getRoleByName(String rolename) throws Exception {
		RtRole role = null;
		try {
			role = roleMapper.getRoleByName(rolename);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		if (role == null) {
			LOG.error("角色名称：{},该角色不存在！", rolename);
			throw new Exception();
		}
		return role;
	}

	public RtRole getRoleById(Long roleid) throws Exception {
		RtRole role = null;
		try {
			role = roleMapper.getRoleById(roleid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		if (role == null) {
			LOG.error("角色ID：{},该角色不存在！", roleid);
			throw new Exception();
		}
		return role;
	}
	
	public Page<RtRole> getRolePageList(int pagenum, int pagesize) throws Exception {
		Page<RtRole> roleList = null;
		try {
			LOG.info("分页查询角色表,页号：{},单页容量：{}",pagenum,pagesize);
			PageHelper.startPage(pagenum, pagesize);
			roleList = roleMapper.getRolePageList();
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}

		if (roleList == null) {
			throw new Exception();
		}
		return roleList;
	}
	
	public void addRole(RtRole role) throws Exception {
		
		if (role == null) {
			LOG.error("角色对象为Null，无法执行插入操作！");
			throw new Exception();
		}
		
		try {
			this.roleMapper.addRole(role);
			LOG.debug("角色名称：{},新增成功！", role.getRolename());
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
	}
	
	
	public void uptRole(RtRole role) throws Exception {
		if (role == null) {
			LOG.error("角色对象为Null，无法执行更新操作！");
			throw new Exception();
		}
		
		try {
			this.roleMapper.uptRole(role);
			LOG.debug("角色名称：{},更新成功！", role.getRolename());
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
	}
	
	public void delRoleById(Long roleid) throws Exception {
		if (roleid == null) {
			LOG.error("角色ID为Null，无法执行更新操作！");
			throw new Exception();
		}
		
		try {
			this.roleMapper.delRoleById(roleid);
			LOG.debug("角色ID：{},删除成功！", roleid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
	}
	
	
	public List<Long> getRightIdsById(Long roleid) throws Exception {
		List<Long> ids = null;
		try {
			ids = this.roleMapper.getRightIdsById(roleid);
			LOG.debug("角色ID：{},所属权限ID查询成功！", roleid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		
		if (ids == null) {
			LOG.error("角色ID:{},无关联权限！");
			throw new Exception();
		} 
		return ids;
	}
	
	public void addRoleReRight(Long roleid,Long rightid) throws Exception {
		if (roleid == null || rightid == null) {
			LOG.error("角色ID或权限ID为Null，无法执行新增操作！");
			throw new Exception();
		} 
		
		try {
			this.roleMapper.addRoleReRight(roleid, rightid);
			LOG.debug("角色ID：{},权限ID：{},新增成功！", roleid,rightid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		
	}
	
	public void delRoleReRight(Long roleid,Long rightid) throws Exception {
		if (roleid == null || rightid == null) {
			LOG.error("角色ID或权限ID为Null，无法执行新增操作！");
			throw new Exception();
		} 
		
		try {
			this.roleMapper.delRoleReRight(roleid, rightid);
			LOG.debug("角色ID：{},权限ID：{},删除成功！", roleid,rightid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
	}
}
