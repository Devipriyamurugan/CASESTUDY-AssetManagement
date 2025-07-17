package com.hexaware.assetmanagement.mapper;

import com.hexaware.assetmanagement.dto.EmployeeRequestDTO;
import com.hexaware.assetmanagement.dto.EmployeeResponseDTO;
import com.hexaware.assetmanagement.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    // Convert Entity to Response DTO
    public static EmployeeResponseDTO toDTO(Employee employee) {
        if (employee == null) return null;

        return new EmployeeResponseDTO(
            employee.getId(),
            employee.getName(),
            employee.getEmail(),
            employee.getDepartment(),
            employee.getDesignation(),
            employee.getContactNumber(),
            employee.getJoinDate(),
            employee.isActive()
        );
    }

    // Convert Response DTO to Entity
    public static Employee toEntity(EmployeeResponseDTO dto) {
        if (dto == null) return null;

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setContactNumber(dto.getContactNumber());
        employee.setJoinDate(dto.getJoinDate());
        employee.setActive(dto.isActive());

        return employee;
    }

    // Convert Request DTO to Entity (used for create or update operations)
    public static Employee toEntity(EmployeeRequestDTO dto) {
        if (dto == null) return null;

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setContactNumber(dto.getContactNumber());
        employee.setJoinDate(dto.getJoinDate());
        employee.setActive(dto.isActive());

        return employee;
    }


}
