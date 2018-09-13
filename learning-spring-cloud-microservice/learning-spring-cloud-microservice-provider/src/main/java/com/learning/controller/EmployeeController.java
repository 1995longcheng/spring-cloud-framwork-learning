package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.learning.entity.Employee;
import com.learning.entity.Project;
import com.learning.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
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
		Project project = employeeService.getProject(id);
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
		Employee employee = employeeService.getEmployee(id);
		if (employee == null) 
			json = "the table of employee not exist employee_id="+id;
		else
			json = JSON.toJSONString(employee);
		
		System.out.println(json);
		return json;
	}
	
	//http://localhost:9001/insertProject?project_id=100001&project_start_time=2002/04/02 10:50:25&project_name=资源池&company_name=凯通
	@GetMapping(value="/insertProject")
	public String insertProject(Project project) {
		employeeService.insertProject(project);
		return "project_id=" + project.getProject_id() + " ,insert into the table of project!";
	}
	
	
	//http://localhost:9001/insertEmployee?employee_id=100001&employee_birth=2002/04/02 10:50:25&employee_name=熊超群&employee_sex=男&employee_age=16&project_id=100001
	//http://localhost:9001/insertEmployee?employee_id=100002&employee_birth=2003/03/04 10:50:25&employee_name=周东&employee_sex=女&employee_age=15&project_id=100001
	@GetMapping(value="/insertEmployee")
	public String insertEmployee(Employee employee) {
		employeeService.insertEmployee(employee);
		return "employee_id=" + employee.getEmployee_id() + " ,insert into the table of employee!";
	}
	
	//http://localhost:9001/setEmployeeRedis?employee_id=100001&employee_birth=2002/04/02 10:50:25&employee_name=熊超群&employee_sex=男&employee_age=16&project_id=100001
	//http://localhost:9001/setEmployeeRedis?employee_id=100002&employee_birth=2003/03/04 10:50:25&employee_name=周东&employee_sex=女&employee_age=15&project_id=100001
	@GetMapping(value="/setEmployeeRedis")
	public String setEmployeeRedis(Employee employee) {
		employeeService.setEmployeeRedis(employee);
		return "employee_id=" + employee.getEmployee_id() + " ,insert into the redis of employee!";
	}
	
	//http://localhost:9001/getEmployee/100001
	//http://localhost:9001/getEmployee/100002
	@GetMapping(value="/getEmployeeRedis/{id}")
	public String getEmployeeRedis(@PathVariable String id) {
		String json = employeeService.getEmployeeRedis(id);
		if (json == null) 
			json = "the redis of employee not exist employee_id="+id;
		return json;
	}
	
	//http://localhost:9001/deleteEmployeeRedis?id=100001
	//http://localhost:9001/deleteEmployeeRedis?id=100002
	@GetMapping(value="/deleteEmployeeRedis")
	public String deleteEmployeeRedis(@RequestParam String id) {
		String json ="delete from the redis of employee success, employee_id = "+id;
		if (!employeeService.deleteEmployeeRedis(id)) {
			json ="delete from the redis of employee failure, employee_id = "+id;
		}
		return json;
	}
	
}
