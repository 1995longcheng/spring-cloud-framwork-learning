package com.learning.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.learning.entity.Employee;

@Repository
public class EmployeeRedisDao {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeRedisDao.class);

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public String getEmployee(String id) {
		String json = redisTemplate.opsForValue().get(id);
		Employee obj = JSON.parseObject(json, Employee.class);
		return JSON.toJSONString(obj);
	}
	
	public boolean deleteEmployeeRedis(String id) {
		return redisTemplate.delete(id);
	}
	
	public void setEmployee(Employee employee) {
		String json = JSON.toJSONString(employee);
		
		if (StringUtils.isEmpty(employee.getEmployee_id())) {
			LOG.debug("ID属性为空！");
			return;
		}
		redisTemplate.opsForValue().set(employee.getEmployee_id()+"", json);
	}
	
}
