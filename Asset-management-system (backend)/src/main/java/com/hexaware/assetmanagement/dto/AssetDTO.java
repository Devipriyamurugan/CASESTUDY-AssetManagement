package com.hexaware.assetmanagement.dto;

import java.time.LocalDate;

public class AssetDTO {

    private int id;
    private String name;
    private String assetNumber;
    private String status;            // AVAILABLE, ASSIGNED, etc.
    private String assetCondition;    // GOOD, DAMAGED, etc.
    private LocalDate purchasedDate;

    // Optional short display fields instead of full entities
    private String categoryName;
    private String assignedToName;      // while connectinng with backend make sure the assigned to filed is empty /null and asset condition is good and status is available.. 
    private int assignedToId; // Add this
                             

    // Constructors
    public AssetDTO() {}

    public AssetDTO(int id, String name, String assetNumber, String status,
                    String assetCondition, LocalDate purchasedDate,
                    String categoryName, String assignedToName,int assignedToId) {
        this.id = id;
        this.name = name;
        this.assetNumber = assetNumber;
        this.status = status;
        this.assetCondition = assetCondition;
        this.purchasedDate = purchasedDate;
        this.categoryName = categoryName;
        this.assignedToName = assignedToName;
        this.assignedToId=assignedToId;
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

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(String assetCondition) {
        this.assetCondition = assetCondition;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }
    
    public int getAssignedToId() {
        return assignedToId;
    }
    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

}
