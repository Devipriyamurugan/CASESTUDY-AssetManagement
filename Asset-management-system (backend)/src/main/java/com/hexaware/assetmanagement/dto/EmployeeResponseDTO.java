package com.hexaware.assetmanagement.dto;

import java.time.LocalDate;

public class EmployeeResponseDTO {

    private int id;
    private String name;
    private String email;
    private String department;
    private String designation;
    private String contactNumber;
    private LocalDate joinDate;
    private boolean active;

    // Constructors
    public EmployeeResponseDTO() {}

    public EmployeeResponseDTO(int id, String name, String email, String department,
                               String designation, String contactNumber,
                               LocalDate joinDate, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.joinDate = joinDate;
        this.active = active;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
