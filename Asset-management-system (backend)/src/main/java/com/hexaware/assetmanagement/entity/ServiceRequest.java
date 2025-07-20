package com.hexaware.assetmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

import com.hexaware.assetmanagement.enums.IssueType;
import com.hexaware.assetmanagement.enums.ServiceStatus;

@Entity
@Table(name = "service_request")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @NotBlank(message = "Description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @PastOrPresent
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    // No-arg constructor
    public ServiceRequest() {}

    // All-arg constructor
    public ServiceRequest(int id, IssueType issueType, String description,
                          ServiceStatus status, LocalDate requestDate,
                          Employee employee, Asset asset) {
        this.id = id;
        this.issueType = issueType;
        this.description = description;
        this.status = status;
        this.requestDate = requestDate;
        this.employee = employee;
        this.asset = asset;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    // toString
    @Override
    public String toString() {
        return "ServiceRequest{" +
                "id=" + id +
                ", issueType=" + issueType +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", requestDate=" + requestDate +
                ", employeeId=" + (employee != null ? employee.getId() : null) + //avoid nullpointer exception
                ", assetId=" + (asset != null ? asset.getId() : null) +
                '}';
    }
}
