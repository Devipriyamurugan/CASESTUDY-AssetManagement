package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.dao.AssetRepository;
import com.hexaware.assetmanagement.dao.AuditRequestRepository;
import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dto.AuditRequestDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.AuditRequest;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.AuditRequestMapper;
import com.hexaware.assetmanagement.serviceimpl.AuditRequestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    @SpringBootTest
    class AuditRequestServiceTest {

        @InjectMocks
        private AuditRequestService service;

        @Mock
        private AuditRequestRepository auditRepo;

        @Mock
        private EmployeeRepository employeeRepo;

        @Mock
        private AssetRepository assetRepo;

        private AuditRequestDTO sampleDTO;
        private AuditRequest sampleEntity;
        private Employee sampleEmployee;
        private Asset sampleAsset;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);

            sampleEmployee = new Employee();
            sampleEmployee.setId(1);
            sampleAsset = new Asset();
            sampleAsset.setId(10);

            sampleEntity = new AuditRequest();
            sampleEntity.setId(100);
            sampleEntity.setAuditDescrption("Check asset status");
            sampleEntity.setStatus("PENDING");
            sampleEntity.setAction("PENDING");
            sampleEntity.setPerformedBy("Admin");
            sampleEntity.setAuditDate(LocalDateTime.now());
            sampleEntity.setEmployee(sampleEmployee);
            sampleEntity.setAsset(sampleAsset);

            sampleDTO = new AuditRequestDTO();
            sampleDTO.setAuditDescrption("Check asset status");
            sampleDTO.setEmployeeId(1);
            sampleDTO.setAssetId(10);
            sampleDTO.setPerformedBy("Admin");
            sampleDTO.setAction("VERIFIED");
        }

        @Test
        void testCreateRequestFromAdmin_Success() {
            when(employeeRepo.findById(1)).thenReturn(Optional.of(sampleEmployee));
            when(assetRepo.findById(10)).thenReturn(Optional.of(sampleAsset));
            when(auditRepo.save(any())).thenReturn(sampleEntity);

            AuditRequestDTO result = service.createRequestFromAdmin(sampleDTO);

            assertEquals("Check asset status", result.getAuditDescrption());
            verify(auditRepo, times(1)).save(any(AuditRequest.class));
        }

        @Test
        void testCreateRequestFromAdmin_EmployeeNotFound() {
            when(employeeRepo.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class,
                    () -> service.createRequestFromAdmin(sampleDTO));
        }

        @Test
        void testRespondToAuditRequest_Success() {
            sampleDTO.setAuditDescrption("Verified successfully");
            sampleDTO.setPerformedBy("Employee1");
            sampleDTO.setAction("VERIFIED");

            when(auditRepo.findById(100)).thenReturn(Optional.of(sampleEntity));
            when(auditRepo.save(any())).thenReturn(sampleEntity);

            AuditRequestDTO result = service.respondToAuditRequest(100, sampleDTO);

            assertEquals("VERIFIED", result.getStatus());
            assertEquals("Verified successfully", result.getAuditDescrption());
            verify(auditRepo, times(1)).save(any(AuditRequest.class));
        }

        @Test
        void testRespondToAuditRequest_NotFound() {
            when(auditRepo.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class,
                    () -> service.respondToAuditRequest(999, sampleDTO));
        }

        @Test
        void testGetAllLogs() {
            when(auditRepo.findAll()).thenReturn(List.of(sampleEntity));
            List<AuditRequestDTO> result = service.getAllLogs();

            assertEquals(1, result.size());
        }

        @Test
        void testGetLogById_Found() {
            when(auditRepo.findById(100)).thenReturn(Optional.of(sampleEntity));

            AuditRequestDTO result = service.getLogById(100);
            assertEquals("Check asset status", result.getAuditDescrption());
        }

        @Test
        void testGetLogById_NotFound() {
            when(auditRepo.findById(999)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> service.getLogById(999));
        }

        @Test
        void testGetLogsByEmployee() {
            when(auditRepo.findByEmployeeId(1)).thenReturn(List.of(sampleEntity));

            List<AuditRequestDTO> result = service.getLogsByEmployee(1);
            assertEquals(1, result.size());
        }

        @Test
        void testGetLogsByDate() {
            LocalDate today = LocalDate.now();
            when(auditRepo.findByAuditDateBetween(any(), any()))
                    .thenReturn(List.of(sampleEntity));

            List<AuditRequestDTO> result = service.getLogsByDate(today);
            assertEquals(1, result.size());
        }
    }



