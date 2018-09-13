DROP TABLE employee;
CREATE TABLE employee(
	employee_name VARCHAR(255),
	employee_sex VARCHAR(32),
	employee_id BIGINT,
	employee_age INT(8),
	employee_birth DATE,
	project_id BIGINT
);


DROP TABLE project;
CREATE TABLE project(
	project_id BIGINT,
	project_name VARCHAR(255),
	company_name VARCHAR(32),
	project_start_time DATE
);