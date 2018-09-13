package com.learning.repository;

import org.apache.ibatis.annotations.Param;

import com.learning.entity.Employee;
import com.learning.entity.Project;

public interface EmployeeDao {
	Employee getEmployee(@Param(value = "employee_id") String id);
	Project getProject(@Param(value = "project_id") String id);
	void insertEmployee(Employee employee);
	void insertProject(Project project);
}
