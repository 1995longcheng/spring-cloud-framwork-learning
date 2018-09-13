package com.learning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Employee;
import com.learning.entity.Project;
import com.learning.repository.EmployeeDao;
import com.learning.repository.EmployeeRedisDao;

@Service
public class EmployeeService {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmployeeRedisDao employeeRedisDao;
	
	public Project getProject(String id) {
		return this.employeeDao.getProject(id);
	}
	
	public Employee getEmployee(String id) {
		return this.employeeDao.getEmployee(id);
	}
	
	public void insertProject(Project project) {
		this.employeeDao.insertProject(project);
	}
	
	public void insertEmployee(Employee employee) {
		this.employeeDao.insertEmployee(employee);
	}
	
	public String getEmployeeRedis(String id) {
		return this.employeeRedisDao.getEmployee(id);
	}
	
	public void setEmployeeRedis(Employee employee) {
		this.employeeRedisDao.setEmployee(employee);
	}
	
	public boolean deleteEmployeeRedis(String id) {
		return this.employeeRedisDao.deleteEmployeeRedis(id);
	}
}
