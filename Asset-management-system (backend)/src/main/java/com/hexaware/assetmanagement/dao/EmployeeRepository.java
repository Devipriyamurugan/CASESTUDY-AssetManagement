package com.hexaware.assetmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

	Employee findByName(String assignedToName);
	
	long count();

	long countByActive(boolean active);


}
