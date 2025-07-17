package com.hexaware.assetmanagement.dto;

public class AdminResponseDTO {

    private int id;
    private String name;
    private String email;
    private String contactNumber;
    private String department;
    private boolean active;

    // Constructors
    public AdminResponseDTO() {}

    public AdminResponseDTO(int id, String name, String email, String contactNumber,
                            String department, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.department = department;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
