package com.hexaware.assetmanagement.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dto.EmployeeRequestDTO;
import com.hexaware.assetmanagement.dto.EmployeeResponseDTO;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.EmployeeMapper;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository repo;

    // Get all employees as DTOs
    public List<EmployeeResponseDTO> getAllEmployee() {
        return repo.findAll()
                   .stream()
                   .map(EmployeeMapper::toDTO)
                   .collect(Collectors.toList());
    }

    // Get single employee by ID
    public EmployeeResponseDTO getEmployeeById(int id) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.toDTO(emp);
    }

    // Save employee and return as DTO
    public EmployeeResponseDTO addEmployee(EmployeeRequestDTO dto) {
        Employee employee = EmployeeMapper.toEntity(dto);
        Employee saved = repo.save(employee);
        return EmployeeMapper.toDTO(saved);
    }

    // Update employee
    public EmployeeResponseDTO updateEmployee(int id, EmployeeRequestDTO dto) {
        Employee existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setDepartment(dto.getDepartment());
        existing.setDesignation(dto.getDesignation());
        existing.setContactNumber(dto.getContactNumber());
        existing.setJoinDate(dto.getJoinDate());
        existing.setActive(dto.isActive());

        Employee updated = repo.save(existing);
        return EmployeeMapper.toDTO(updated);
    }

    // Delete employee
    public EmployeeResponseDTO deleteEmployee(int id) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        repo.deleteById(id);
        return EmployeeMapper.toDTO(emp);
    }
}
