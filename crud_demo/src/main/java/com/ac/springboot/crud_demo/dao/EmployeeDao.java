package com.ac.springboot.crud_demo.dao;

import java.util.List;

import com.ac.springboot.crud_demo.entity.Employee;

public interface EmployeeDao {
	List<Employee> getAllEmployees();
	List<Employee> getEmployeeByName(String name);
	Employee findById(int id);
	Employee addEmployee(Employee e);
	//void updateEmployee(Employee e)
	void  deleteEmployeeById(int id);
}
