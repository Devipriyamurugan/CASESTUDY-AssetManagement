package com.hexaware.assetmanagement.dto;

import java.util.List;

public class AssignedAssetsDTO {
    private String employeeName;
    private String email;
    private String department;
    private String phoneNumber;
    private boolean active;
    private List<String> assetNames;

    // Constructors
    public AssignedAssetsDTO() {}

    public AssignedAssetsDTO(String employeeName, String email, String department,
                            String phoneNumber, boolean active, List<String> assetNames) {
        this.employeeName = employeeName;
        this.email = email;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.assetNames = assetNames;
    }

    // Getters and Setters

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getAssetNames() {
        return assetNames;
    }

    public void setAssetNames(List<String> assetNames) {
        this.assetNames = assetNames;
    }
}
