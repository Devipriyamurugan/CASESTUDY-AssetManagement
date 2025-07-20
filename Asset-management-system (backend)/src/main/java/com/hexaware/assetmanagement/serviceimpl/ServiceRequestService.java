package com.hexaware.assetmanagement.serviceimpl;

import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.entity.*;
import com.hexaware.assetmanagement.enums.AssetStatus;
import com.hexaware.assetmanagement.enums.IssueType;
import com.hexaware.assetmanagement.enums.ServiceStatus;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.ServiceRequestMapper;
import com.hexaware.assetmanagement.dao.AssetRepository;
import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dao.ServiceRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository requestRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AssetRepository assetRepo;

    // Get all service requests
    public List<ServiceRequestDTO> getAllRequests() {
        return requestRepo.findAll()
                .stream()
                .map(ServiceRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get request by ID
    public ServiceRequestDTO getRequestById(int id) {
        return requestRepo.findById(id)
                .map(ServiceRequestMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));
    }

    // Add a new service request
    public ServiceRequestDTO addRequest(ServiceRequestDTO dto) {
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId()));

        Asset asset = assetRepo.findById(dto.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + dto.getAssetId()));

        ServiceRequest request = new ServiceRequest();
        request.setIssueType(IssueType.valueOf(dto.getIssueType().toUpperCase()));
        request.setDescription(dto.getDescription());
        request.setStatus(ServiceStatus.valueOf(dto.getStatus().toUpperCase()));
        request.setRequestDate(dto.getRequestDate());
        request.setEmployee(emp);
        request.setAsset(asset);
        asset.setStatus(AssetStatus.REQUESTED);  // <-- this line is the key
        assetRepo.save(asset); 

        return ServiceRequestMapper.toDTO(requestRepo.save(request));
    }

    // Update service request
    public ServiceRequestDTO updateRequest(int id, ServiceRequestDTO dto) {
        ServiceRequest request = requestRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found with id: " + id));

        request.setDescription(dto.getDescription());
        request.setStatus(ServiceStatus.valueOf(dto.getStatus().toUpperCase()));

        return ServiceRequestMapper.toDTO(requestRepo.save(request));
    }

    // Delete request
    public boolean deleteRequest(int id) {
        if (!requestRepo.existsById(id)) {
            throw new ResourceNotFoundException("Service request not found with id: " + id);
        }
        requestRepo.deleteById(id);
        return true;
    }

    // Filter by employee
    public List<ServiceRequestDTO> getRequestsByEmployee(int eid) {
        return requestRepo.findByEmployeeId(eid)
                .stream()
                .map(ServiceRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Filter by status
    public List<ServiceRequestDTO> getRequestsByStatus(String status) {
        ServiceStatus enumStatus;
        try {
            enumStatus = ServiceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Invalid service request status: " + status);
        }

        return requestRepo.findByStatus(enumStatus)
                .stream()
                .map(ServiceRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
    
 // Total number of service requests raised by employee
    public int countTotalRequests(int employeeId) {
        return requestRepo.countByEmployeeId(employeeId);  // ✅
    }

    // Only pending service requests
    public int countPendingRequests(int employeeId) {
        return requestRepo.countByEmployeeIdAndStatus(
            employeeId, com.hexaware.assetmanagement.enums.ServiceStatus.PENDING
        );
    }

}
