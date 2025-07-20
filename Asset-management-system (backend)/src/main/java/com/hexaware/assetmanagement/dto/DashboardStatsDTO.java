package com.hexaware.assetmanagement.dto;

public class DashboardStatsDTO {

    private long totalEmployees;
    private long totalAssets;
    private long totalServiceRequests;
    private long pendingServiceRequests;


    // Constructors
    public DashboardStatsDTO() {}

    public DashboardStatsDTO(long totalEmployees, long totalAssets, long totalServiceRequests, long pendingServiceRequests) {
        this.totalEmployees = totalEmployees;
        this.totalAssets = totalAssets;
        this.totalServiceRequests = totalServiceRequests;
        this.pendingServiceRequests = pendingServiceRequests;
    }

    // Getters and Setters
    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public long getTotalServiceRequests() {
        return totalServiceRequests;
    }

    public void setTotalServiceRequests(long totalServiceRequests) {
        this.totalServiceRequests = totalServiceRequests;
    }

    public long getPendingServiceRequests() {
        return pendingServiceRequests;
    }

    public void setPendingServiceRequests(long pendingServiceRequests) {
        this.pendingServiceRequests = pendingServiceRequests;
    }
}
