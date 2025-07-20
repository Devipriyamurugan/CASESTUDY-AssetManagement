

package com.hexaware.assetmanagement.dto;

import java.time.LocalDateTime;

public class AuditRequestDTO {

    private int id;
    private String status;             // PENDING, VERIFIED, REJECTED
    private String action;             // Verified / Rejected
    private String performedBy;
    private String auditDescrption;
    private String adminNote;         // Note sent by admin
    private String employeeResponse;  // Optional comment by employee
    private LocalDateTime auditDate;

    private int employeeId;
    private String employeeName;

    private int assetId;
    private String assetName;

    // No-arg constructor
    public AuditRequestDTO() {}

    // All-arg constructor
    public AuditRequestDTO(int id, String status, String action, String performedBy, String auditDescrption,
                           String adminNote, String employeeResponse, LocalDateTime auditDate,
                           int employeeId, String employeeName, int assetId, String assetName) {
        this.id = id;
        this.status = status;
        this.action = action;
        this.performedBy = performedBy;
        this.auditDescrption = auditDescrption;
        this.adminNote = adminNote;
        this.employeeResponse = employeeResponse;
        this.auditDate = auditDate;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.assetId = assetId;
        this.assetName = assetName;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public String getAuditDescrption() { return auditDescrption; }
    public void setAuditDescrption(String auditDescrption) { this.auditDescrption = auditDescrption; }

    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }

    public String getEmployeeResponse() { return employeeResponse; }
    public void setEmployeeResponse(String employeeResponse) { this.employeeResponse = employeeResponse; }

    public LocalDateTime getAuditDate() { return auditDate; }
    public void setAuditDate(LocalDateTime auditDate) { this.auditDate = auditDate; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public int getAssetId() { return assetId; }
    public void setAssetId(int assetId) { this.assetId = assetId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
}

