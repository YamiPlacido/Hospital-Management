package com.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hospital.model.*;
import com.hospital.repository.*;

@Service
@Transactional
@Repository
@Component
public class EmployeeServiceImpl implements EmployeeService {
   
	 
	 @Autowired
	 EmployeeRepository EmployeeRepository;
	 
	 @Autowired
	 PatientRepository PatientRepository;
	 
	 


	 @Override
	 public List<Employee> getAllEmployee() {
		 List<Employee> employeeList = (List<Employee>) EmployeeRepository.findAll();
		 return employeeList;
	 }
	 
	
	 @Override
	 public Employee getEmployeeById(long employeeId) {
	  return EmployeeRepository.findById(employeeId).get();
	 }

	 @Override
	 public void saveOrUpdate(Employee employee) {
		 EmployeeRepository.save(employee);
	 }

	 @Override
	 public void deleteEmployee(long employeeId) {
		 EmployeeRepository.deleteById(employeeId);
	 }

	}

