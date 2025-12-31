package com.ac.springboot.crud_demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ac.springboot.crud_demo.entity.Employee;
import com.ac.springboot.crud_demo.servvice.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

//import jakarta.transaction.Transactional;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")
public class EmployeeController {
	EmployeeService employeeService;
	@Autowired
	public EmployeeController(EmployeeService employeeService,ObjectMapper objectmapper) {
		this.employeeService=employeeService;
	}
	
	
	
	@GetMapping("/employees")
	List<Employee> getEmployees(){
		return employeeService.getAllEmployees();
	}
	@GetMapping("/employees/{name}")
	public List<Employee> getEmployee(@PathVariable String name) {
		List<Employee> theEmployee=employeeService.getEmployeeByName(name);
		if(theEmployee==null) {
			throw new RuntimeException("No employee found");
		}
		return theEmployee;
	}
	//add mapping for posting student details
	@PostMapping("/employees")
	public void addStudent(@RequestBody Employee e) {
		employeeService.addEmployee(e);
	}
	
	//add mapping for put / employees - update existing employees
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee e) {
		return employeeService.addEmployee(e);
	}
	// add patch mapping so that partial updation can be done
	@PatchMapping("/employees/{id}")
	public Employee patchEmployee(@PathVariable int id, @RequestBody Map<String,Object> patchpayLoad) {
		Employee tempEmployee=(Employee) employeeService.findById(id);
		if(tempEmployee==null) throw new RuntimeException("Employee not Found");
		if(patchpayLoad.containsKey("id")) throw new RuntimeException("Nt allowed to change primary key");
		Employee patchedEmployee=apply(patchpayLoad,tempEmployee);
		Employee dbEmployee=employeeService.addEmployee(patchedEmployee);
		return dbEmployee;
		
	}


	private final ObjectMapper mapper = new ObjectMapper(); 
	private Employee apply(Map<String, Object> patchpayLoad, Employee tempEmployee) {
		// TODO Auto-generated method stub
		//convert employee object to a JSON object node
		ObjectNode employeeNode=mapper.convertValue(tempEmployee,ObjectNode.class);
		ObjectNode patchNode=mapper.convertValue(patchpayLoad,ObjectNode.class);
		employeeNode.setAll(patchNode);
		return mapper.convertValue(employeeNode,Employee.class);
	}
	
	//add mappings for deleting employee
	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable int id) {
		Employee emp=employeeService.findById(id);
		if(emp==null) throw new RuntimeException("Employee not found");
		employeeService.deleteEmployeeById(id);
	}
}
