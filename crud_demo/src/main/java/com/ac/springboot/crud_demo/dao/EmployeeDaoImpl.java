package com.ac.springboot.crud_demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ac.springboot.crud_demo.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
//import jakarta.transaction.Transactional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private EntityManager entityManager;
	@Autowired
	EmployeeDaoImpl(EntityManager entityManager){
		this.entityManager=entityManager;	}
	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		
		TypedQuery<Employee> theQuery=entityManager.createQuery("from Employee", Employee.class);
		
		return theQuery.getResultList();
	}

	@Override
	public List<Employee> getEmployeeByName(String name) {
	    String jpql = "SELECT e FROM Employee e WHERE e.firstname = :name";
	    return entityManager.createQuery(jpql, Employee.class)
	                        .setParameter("name", name)
	                        .getResultList();
	}

	@Override
	public Employee addEmployee(Employee e) {
		// TODO Auto-generated method stub
		Employee dbEmployee=entityManager.merge(e);
		return dbEmployee;
	}
	@Override
	public void deleteEmployeeById(int id) {
		// TODO Auto-generated method stub
		Employee e= entityManager.find(Employee.class,id);
		entityManager.remove(e);
	}
	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		Employee e=entityManager.find(Employee.class, id);
		return e;
	}

}
