package com.hexaware.assetmanagement.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String password;
    private String department;
    private String designation;
    private String contactNumber;
    private LocalDate joinDate;
    private boolean active;

    @OneToMany(mappedBy = "assignedTo")
    private List<Asset> assets;
//
   @OneToMany(mappedBy = "employee")
    private List<ServiceRequest> serviceRequests;
//
    @OneToMany(mappedBy = "employee")
    private List<AuditRequest> auditRequests;

    // No-arg constructor
    public Employee() {
    }

    // All-arg constructor
    public Employee(int id, String name, String email, String password, String department,
                    String designation, String contactNumber, LocalDate joinDate, boolean active,
                    List<Asset> assets, List<ServiceRequest> serviceRequests, List<AuditRequest> auditRequests) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.joinDate = joinDate;
        this.active = active;
        this.assets = assets;
        this.serviceRequests = serviceRequests;
        this.auditRequests = auditRequests;
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

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public List<AuditRequest> getAuditRequests() {
        return auditRequests;
    }

    public void setAuditRequests(List<AuditRequest> auditRequests) {
        this.auditRequests = auditRequests;
    }

    // toString()
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", joinDate=" + joinDate +
                ", active=" + active +
                '}';
    }
}
