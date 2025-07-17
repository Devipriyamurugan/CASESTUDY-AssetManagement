package com.hexaware.assetmanagement.mapper;

import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.entity.ServiceRequest;
import com.hexaware.assetmanagement.enums.IssueType;
import com.hexaware.assetmanagement.enums.ServiceStatus;

public class ServiceRequestMapper {

    // Convert Entity -> DTO
    public static ServiceRequestDTO toDTO(ServiceRequest request) {
        return new ServiceRequestDTO(
            request.getId(),
            request.getIssueType().toString(),
            request.getDescription(),
            request.getStatus().toString(),
            request.getRequestDate(),
            request.getAsset() != null ? request.getAsset().getId() : 0,
            request.getAsset() != null ? request.getAsset().getName() : null,
            request.getEmployee() != null ? request.getEmployee().getId() : 0,
            request.getEmployee() != null ? request.getEmployee().getName() : null
        );
    }

    // Convert DTO -> Entity (for saving/updating)
    public static ServiceRequest toEntity(ServiceRequestDTO dto, Asset asset, Employee employee) {
        return new ServiceRequest(
            dto.getId(),
            IssueType.valueOf(dto.getIssueType().toUpperCase()),
            dto.getDescription(),
            ServiceStatus.valueOf(dto.getStatus().toUpperCase()),
            dto.getRequestDate(),
            employee,
            asset
        );
    }
}
