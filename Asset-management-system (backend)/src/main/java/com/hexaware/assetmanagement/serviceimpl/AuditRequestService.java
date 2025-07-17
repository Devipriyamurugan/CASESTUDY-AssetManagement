package com.hexaware.assetmanagement.serviceimpl;

import com.hexaware.assetmanagement.dto.AuditRequestDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.AuditRequest;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.AuditRequestMapper;
import com.hexaware.assetmanagement.dao.AssetRepository;
import com.hexaware.assetmanagement.dao.AuditRequestRepository;
import com.hexaware.assetmanagement.dao.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class AuditRequestService {

    @Autowired
    private AuditRequestRepository auditRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AssetRepository assetRepo;

    // ADMIN: Create audit request
    public AuditRequestDTO createRequestFromAdmin(AuditRequestDTO dto) {
        Employee emp = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmployeeId()));

        Asset asset = assetRepo.findById(dto.getAssetId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + dto.getAssetId()));

        AuditRequest entity = new AuditRequest();
        entity.setStatus("PENDING");
        entity.setAction("PENDING");
        entity.setPerformedBy(dto.getPerformedBy()); // Admin
        entity.setAuditDescrption(dto.getAuditDescrption());
        entity.setAuditDate(LocalDateTime.now());
        entity.setAdminNote(dto.getAuditDescrption());
        entity.setEmployee(emp);
        entity.setAsset(asset);

        return AuditRequestMapper.toDTO(auditRepo.save(entity));
    }

    // EMPLOYEE: Respond to audit
    public AuditRequestDTO respondToAuditRequest(int id, AuditRequestDTO dto) {
        AuditRequest entity = auditRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit request not found with id: " + id));

        entity.setAction(dto.getAction());
        entity.setStatus(dto.getAction().toUpperCase()); // VERIFIED or REJECTED
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setAuditDescrption(dto.getAuditDescrption());
        entity.setAuditDate(LocalDateTime.now());
        entity.setEmployeeResponse(dto.getAuditDescrption());

        return AuditRequestMapper.toDTO(auditRepo.save(entity));
    }

    public List<AuditRequestDTO> getAllLogs() {
        return auditRepo.findAll().stream().map(AuditRequestMapper::toDTO).collect(Collectors.toList());
    }

    public AuditRequestDTO getLogById(int id) {
        return auditRepo.findById(id).map(AuditRequestMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Audit log not found with id: " + id));
    }

    public List<AuditRequestDTO> getLogsByEmployee(int employeeId) {
        return auditRepo.findByEmployeeId(employeeId).stream().map(AuditRequestMapper::toDTO).collect(Collectors.toList());
    }

    public List<AuditRequestDTO> getLogsByDate(LocalDate date) {
        return auditRepo.findByAuditDateBetween(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .stream().map(AuditRequestMapper::toDTO).collect(Collectors.toList());
    }
}
