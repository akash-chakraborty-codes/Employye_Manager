package com.ac.springboot.crud_demo.servvice;

import java.util.List;

import com.ac.springboot.crud_demo.entity.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
	List<Employee> getEmployeeByName(String name);
	Employee findById(int id);
	Employee addEmployee(Employee e);
	//void updateEmployee(Employee e);
	void deleteEmployeeById(int id);
}
