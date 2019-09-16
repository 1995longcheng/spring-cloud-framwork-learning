package com.learning.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.learning.security.entity.RtRight;
import com.learning.security.mapper.RtRightMapper;

@Service
public class RtRightService {
	private static final Logger LOG = LoggerFactory.getLogger(RtRightService.class);
	
	@Autowired
	private RtRightMapper rightMapper;

	public RtRight getRightByName(String rightname) throws Exception {
		RtRight right = null;
		try {
			right = rightMapper.getRightByName(rightname);
		} catch (Exception e) {
			LOG.error("权限名称：{},该权限不存在！",rightname,e);
			throw e;
		}
		if (right == null) {
			LOG.error("权限名称：{},该权限不存在！",rightname);
			throw new Exception();
		}
		return right;
	}

	public RtRight getRightById(Long rightid) throws Exception {
		RtRight right = null;
		try {
			right = rightMapper.getRightById(rightid);
		} catch (Exception e) {
			LOG.error("权限ID：{},该权限不存在！", rightid,e);
			throw e;
		}
		if (right == null) {
			LOG.error("权限ID：{},该权限不存在！", rightid);
			throw new Exception();
		}
		return right;
	}
	
	/**
	 * 分页查询
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	public Page<RtRight> getRightPageList(int pagenum, int pagesize) throws Exception {
		LOG.info("分页查询权限表,页号：{},单页容量：{}",pagenum,pagesize);
		Page<RtRight> rightList = null;
		try {
			PageHelper.startPage(pagenum, pagesize);
			rightList = rightMapper.getRightPageList();
		} catch (Exception e) {
			LOG.error("分页查询异常!",e);
			throw e;
		}
		
		if (rightList == null) {
			throw new Exception();
		}
		return rightList;
	}
	
	public void addRight(RtRight right) throws Exception {
		if (right == null) {
			LOG.error("权限对象为Null，无法执行插入操作！");
			throw new Exception();
		}
		try {
			this.rightMapper.addRight(right);
			LOG.debug("权限名称：{},新增成功！", right.getRightname());
		} catch (Exception e) {
			LOG.error("新增操作异常!",e);
			throw e;
		}
	}
	
	public void uptRight(RtRight right) throws Exception {
		if (right == null) {
			LOG.error("权限对象为Null，无法执行更新操作！");
			throw new Exception();
		}
		
		try {
			this.rightMapper.uptRight(right);
			LOG.debug("权限名称：{},更新成功！", right.getRightname());
		} catch (Exception e) {
			LOG.error("更新异常！",e);
			throw e;
		}
		
	}
	
	public void delRightById(Long rightid) throws Exception {
		if (rightid == null) {
			LOG.error("权限ID为Null，无法执行更新操作！");
			throw new Exception();
		}
		
		try {
			this.rightMapper.delRightById(rightid);
			LOG.debug("权限ID：{},删除成功！", rightid);
		} catch (Exception e) {
			LOG.error("删除异常！",e);
			throw e;
		}
		
	}
}
