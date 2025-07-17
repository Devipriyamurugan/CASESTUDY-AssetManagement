package com.hexaware.assetmanagement.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dao.UserCredentialsRepository;
import com.hexaware.assetmanagement.dto.UserDetailsDTO;
import com.hexaware.assetmanagement.entity.Admin;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.entity.UserCredentials;
import com.hexaware.assetmanagement.enums.UserRole;
import com.hexaware.assetmanagement.util.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserCredentialsRepository credentialsRepo;

    public UserDetailsDTO getUserProfile(String token, JwtUtil jwtUtil) {
        String username = jwtUtil.extractUsername(token);
        UserCredentials creds = credentialsRepo.findByUsername(username).orElseThrow();

        if (creds.getRole() == UserRole.ADMIN) {
            Admin admin = creds.getAdmin();
            return new UserDetailsDTO(
            	admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getContactNumber(),
                admin.getDepartment(),
                "ADMIN",
                null,
                null
            );
        } else {
            Employee emp = creds.getEmployee();
            return new UserDetailsDTO(
            	emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getContactNumber(),
                emp.getDepartment(),
                "EMPLOYEE",
                emp.getDesignation(),
                emp.getJoinDate().toString()
            );
        }
    }
}
