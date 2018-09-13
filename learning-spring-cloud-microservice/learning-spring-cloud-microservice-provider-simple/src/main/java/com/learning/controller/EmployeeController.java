package com.learning.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.learning.entity.Employee;
import com.learning.entity.Project;

@RestController
public class EmployeeController {
	/**
	 * Json返回对象
	 * {
		"company_name": "凯通",
		"project_employees": [{
			"employee_age": 16,
			"employee_birth": "2002-04-02 00:00:00",
			"employee_id": 100001,
			"employee_name": "熊超群",
			"employee_sex": "男",
			"project_id": 100001
			}, {
			"employee_age": 15,
			"employee_birth": "2003-03-04 00:00:00",
			"employee_id": 100002,
			"employee_name": "周东",
			"employee_sex": "女",
			"project_id": 100001
		}],
		"project_id": 100001,
		"project_name": "资源池",
		"project_start_time": "2002-04-02 00:00:00"
	  }
	 */
	//http://localhost:9001/getProject?id=100001
	@GetMapping(value="/getProject")
	public String getProject(@RequestParam String id) {
		String json;
		Project project = null;
		
		project = new Project();
		project.setCompany_name("凯通");
		project.setProject_name("资源池");
		project.setProject_id(Long.valueOf(id));
		project.setProject_start_time(new Date());
		
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if (project == null) 
			json = "the table of project not exist project_id="+id;
		else
			json = JSON.toJSONString(project);
		return json;
	}
	
	//http://localhost:9001/getEmployee/100001
	//http://localhost:9001/getEmployee/100002
	@GetMapping(value="/getEmployee/{id}")
	public String getEmployee(@PathVariable String id) {
		String json;
		Employee employee = null;
		
		employee = new Employee();
		employee.setEmployee_id(Long.valueOf(id));
		employee.setEmployee_name("熊超群");
		employee.setEmployee_age(25);
		employee.setEmployee_sex("男");
		employee.setProject_id(100001L);
		
		if (employee == null) 
			json = "the table of employee not exist employee_id="+id;
		else
			json = JSON.toJSONString(employee);
		
		System.out.println(json);
		return json;
	}
}
