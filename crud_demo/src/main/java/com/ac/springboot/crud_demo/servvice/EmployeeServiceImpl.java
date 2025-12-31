package com.ac.springboot.crud_demo.servvice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ac.springboot.crud_demo.dao.EmployeeDao;
import com.ac.springboot.crud_demo.entity.Employee;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeDao employeeDao;
	@Autowired
	EmployeeServiceImpl(EmployeeDao employeeDao){
		this.employeeDao=employeeDao;
	}
	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return employeeDao.getAllEmployees();
	}
	@Override
	public List<Employee> getEmployeeByName(String name) {
		// TODO Auto-generated method stub
		return (List<Employee>) employeeDao.getEmployeeByName(name);
	}
	@Override
	@Transactional
	public Employee addEmployee(Employee e) {
		// TODO Auto-generated method stub
		Employee emp= employeeDao.addEmployee(e);
		return emp;
	}
	@Override
	@Transactional
	public void deleteEmployeeById(int id) {
		// TODO Auto-generated metho
		 employeeDao.deleteEmployeeById(id);
	}
	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return employeeDao.findById(id);
	}

}
