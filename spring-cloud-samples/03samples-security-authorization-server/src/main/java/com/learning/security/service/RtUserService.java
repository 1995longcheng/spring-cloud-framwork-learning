package com.learning.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.learning.security.entity.RtUser;
import com.learning.security.mapper.RtUserMapper;

@Service
public class RtUserService {
	private static final Logger LOG = LoggerFactory.getLogger(RtUserService.class);

	@Autowired
	private RtUserMapper userMapper;

	/**
	 * 通过用户名称获取用户信息
	 * 
	 * @param username
	 */
	public RtUser getUserByName(String username) throws Exception {
		RtUser user = null;
		try {
			user = userMapper.getUserByName(username);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		
		if (user == null) {
			LOG.error("用户名称：{},该用户不存在！", username);
			throw new Exception();
		}
		return user;
	}

	public RtUser getUserById(Long userid) throws Exception {
		RtUser user = null;
		try {
			user = userMapper.getUserById(userid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}

		if (user == null) {
			LOG.error("用户ID：{},该用户不存在！", userid);
			throw new Exception();
		}
		return user;
	}
	
	/**
	 * 分页查询
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	public Page<RtUser> getUserPageList(int pagenum, int pagesize) throws Exception {
		Page<RtUser> userList = null;
		try {
			LOG.info("分页查询用户表,页号：{},单页容量：{}",pagenum,pagesize);
			PageHelper.startPage(pagenum, pagesize);
			userList = userMapper.getUserPageList();
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		if (userList == null) {
			throw new Exception();
		}
		return userList;
	}
	
	public void addUser(RtUser user) throws Exception {
		if (user == null) {
			LOG.error("用户对象为Null，无法执行插入操作！");
			throw new Exception();
		}
		
		try {
			this.userMapper.addUser(user);
			LOG.debug("用户名称：{},新增成功！", user.getUsername());
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
	}
	
	public void uptUser(RtUser user) throws Exception {
		if (user == null) {
			LOG.error("用户对象为Null，无法执行更新操作！");
			throw new Exception();
		}
		
		try {
			this.userMapper.uptUser(user);
			LOG.debug("用户名称：{},更新成功！", user.getUsername());
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		
	}
	
	public void delUserById(Long userid) throws Exception {
		if (userid == null) {
			LOG.error("用户ID为Null，无法执行更新操作！");
			throw new Exception();
		}
		
		try {
			this.userMapper.delUserById(userid);
			LOG.debug("用户ID：{},删除成功！", userid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		
	}
	
	
	public List<Long> getRoleIdsByUserId(Long userid) throws Exception {
		List<Long> ids = null;
		try {
			ids = this.userMapper.getRoleIdsByUserId(userid);
			LOG.debug("用户ID：{},所属角色ID查询成功！", userid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		if (ids == null) {
			LOG.error("用户ID:{}，无关联角色！",userid);
			throw new Exception();
		}
		return ids;
	}
	
	public void addUserReRole(Long userid,Long roleid) throws Exception {
		if (userid == null || roleid == null) {
			LOG.error("用户ID或角色ID为Null，无法执行新增操作！");
			throw new Exception();
		} 
		
		try {
			this.userMapper.addUserReRole(userid, roleid);
			LOG.debug("用户ID：{},角色ID：{},新增成功！", userid,roleid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}
		
	}
	
	public void delUserReRole(Long userid,Long roleid) throws Exception {
		if (userid == null || roleid == null) {
			LOG.error("用户ID或角色ID为Null，无法执行删除操作！");
			throw new Exception();
		} 
		
		try {
			this.userMapper.delUserReRole(userid, roleid);
			LOG.debug("用户ID：{},角色ID：{},删除成功！", userid,roleid);
		} catch (Exception e) {
			LOG.error("",e);
			throw e;
		}

	}
	
}
