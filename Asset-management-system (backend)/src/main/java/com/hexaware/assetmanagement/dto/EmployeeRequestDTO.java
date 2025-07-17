package com.hexaware.assetmanagement.dto;

import java.time.LocalDate;

public class EmployeeRequestDTO {

    private String name;
    private String email;
    private String password;
    private String department;
    private String designation;
    private String contactNumber;
    private LocalDate joinDate;
    private boolean active;

    // No-arg constructor
    public EmployeeRequestDTO() {
    }

    // All-arg constructor
    public EmployeeRequestDTO(String name, String email, String password, String department,
                              String designation, String contactNumber,
                              LocalDate joinDate, boolean active) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.joinDate = joinDate;
        this.active = active;
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Optional: toString for logging/debugging
    @Override
    public String toString() {
        return "EmployeeRequestDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='********'" +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", joinDate=" + joinDate +
                ", active=" + active +
                '}';
    }
}
