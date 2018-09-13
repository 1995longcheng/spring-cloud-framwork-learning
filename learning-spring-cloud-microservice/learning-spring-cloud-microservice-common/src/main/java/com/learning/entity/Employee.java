package com.learning.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Employee {
	private String employee_name;
	private String employee_sex;
	private Long employee_id;
	private Integer employee_age;
	
	@JSONField(format="yyyy-MM-dd HH:mm:SS")
	private Date employee_birth;
	
	private Long project_id;//所属项目

	public Long getProject_id() {
		return project_id;
	}
	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmployee_sex() {
		return employee_sex;
	}
	public void setEmployee_sex(String employee_sex) {
		this.employee_sex = employee_sex;
	}
	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
	public Integer getEmployee_age() {
		return employee_age;
	}
	public void setEmployee_age(Integer employee_age) {
		this.employee_age = employee_age;
	}
	public Date getEmployee_birth() {
		return employee_birth;
	}
	public void setEmployee_birth(Date employee_birth) {
		this.employee_birth = employee_birth;
	}
}
