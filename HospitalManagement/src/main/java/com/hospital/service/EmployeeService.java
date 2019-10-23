package com.hospital.service;
import java.util.List;

import com.hospital.model.Employee;

public interface EmployeeService {  

	 public List<Employee> getAllEmployee();
	 
	 public Employee getEmployeeById(long employeeId);
	 
	 public void saveOrUpdate(Employee employee);
	 
	 public void deleteEmployee(long employeeId);

	
	
}
