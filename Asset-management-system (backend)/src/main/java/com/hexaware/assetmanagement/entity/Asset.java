package com.hexaware.assetmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

import com.hexaware.assetmanagement.enums.AssetCondition;
import com.hexaware.assetmanagement.enums.AssetStatus;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Asset name is required")
    private String name;

    @NotBlank(message = "Asset number is required")
    @Column(unique = true)
    private String assetNumber;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @PastOrPresent(message = "Purchase date can't be in the future")
    private LocalDate purchasedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_condition")
    private AssetCondition assetCondition;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee assignedTo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructors
    public Asset() {}

    public Asset(int id, String name, String assetNumber, AssetStatus status,
                 LocalDate purchasedDate, AssetCondition assetCondition,
                 Employee assignedTo, Category category) {
        this.id = id;
        this.name = name;
        this.assetNumber = assetNumber;
        this.status = status;
        this.purchasedDate = purchasedDate;
        this.assetCondition = assetCondition;
        this.assignedTo = assignedTo;
        this.category = category;
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

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public AssetCondition getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(AssetCondition assetCondition) {
        this.assetCondition = assetCondition;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // toString()

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assetNumber='" + assetNumber + '\'' +
                ", status=" + status +
                ", purchasedDate=" + purchasedDate +
                ", assetCondition=" + assetCondition +
                ", assignedTo=" + (assignedTo != null ? assignedTo.getId() : null) +
                ", category=" + (category != null ? category.getId() : null) +
                '}';
    }
}
