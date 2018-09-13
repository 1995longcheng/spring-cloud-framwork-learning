package com.learning.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 项目实体
 * 
 * @author xiongchaoqun
 * @date 2018年8月27日
 */
public class Project {
	private Long project_id;
	private String project_name;
	private String company_name;
	private List<Employee> project_employees;
	
	@JSONField(format="yyyy-MM-dd HH:mm:SS")
	private Date project_start_time;

	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public List<Employee> getProject_employees() {
		return project_employees;
	}

	public void setProject_employees(List<Employee> project_employees) {
		this.project_employees = project_employees;
	}

	public Date getProject_start_time() {
		return project_start_time;
	}

	public void setProject_start_time(Date project_start_time) {
		this.project_start_time = project_start_time;
	}

}
