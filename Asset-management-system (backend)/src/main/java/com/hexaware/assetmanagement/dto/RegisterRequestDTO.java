package com.hexaware.assetmanagement.dto;

import java.time.LocalDate;

public class RegisterRequestDTO {

    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private String department;

    // For employees only
    private String designation;
    private LocalDate joinDate;

    // To differentiate between EMPLOYEE or ADMIN
    private String role;

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String name, String email, String password,
                              String contactNumber, String department,
                              String designation, LocalDate joinDate,
                              String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.department = department;
        this.designation = designation;
        this.joinDate = joinDate;
        this.role = role;
    }

    // Getters & Setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
