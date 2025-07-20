package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.dao.AssetRepository;
import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dao.ServiceRequestRepository;
import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.entity.ServiceRequest;
import com.hexaware.assetmanagement.enums.AssetStatus;
import com.hexaware.assetmanagement.enums.IssueType;
import com.hexaware.assetmanagement.enums.ServiceStatus;
import com.hexaware.assetmanagement.serviceimpl.ServiceRequestService;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class ServiceRequestServiceTest {

        @InjectMocks
        private ServiceRequestService service;

        @Mock
        private ServiceRequestRepository requestRepo;

        @Mock
        private AssetRepository assetRepo;

        @Mock
        private EmployeeRepository employeeRepo;

        private ServiceRequest sampleRequest;
        private ServiceRequestDTO sampleDTO;
        private Employee sampleEmployee;
        private Asset sampleAsset;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);

            sampleEmployee = new Employee();
            sampleEmployee.setId(1);
            sampleEmployee.setName("John Doe");

            sampleAsset = new Asset();
            sampleAsset.setId(10);
            sampleAsset.setStatus(AssetStatus.ASSIGNED);

            sampleRequest = new ServiceRequest();
            sampleRequest.setId(100);
            sampleRequest.setDescription("Screen not working");
            sampleRequest.setIssueType(IssueType.HARDWARE);
            sampleRequest.setStatus(ServiceStatus.PENDING);
            sampleRequest.setRequestDate(LocalDate.now());
            sampleRequest.setEmployee(sampleEmployee);
            sampleRequest.setAsset(sampleAsset);

            sampleDTO = new ServiceRequestDTO();
            sampleDTO.setId(100);
            sampleDTO.setDescription("Screen not working");
            sampleDTO.setIssueType("HARDWARE");
            sampleDTO.setStatus("PENDING");
            sampleDTO.setRequestDate(LocalDate.now());
            sampleDTO.setEmployeeId(1);
            sampleDTO.setAssetId(10);
        }

        @Test
        void testGetAllRequests() {
            when(requestRepo.findAll()).thenReturn(List.of(sampleRequest));
            var result = service.getAllRequests();
            assertEquals(1, result.size());
            verify(requestRepo).findAll();
        }

        @Test
        void testGetRequestById_Found() {
            when(requestRepo.findById(100)).thenReturn(Optional.of(sampleRequest));
            var result = service.getRequestById(100);
            assertEquals("Screen not working", result.getDescription());
        }

        @Test
        void testGetRequestById_NotFound() {
            when(requestRepo.findById(999)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> service.getRequestById(999));
        }

        @Test
        void testAddRequest_Success() {
            when(employeeRepo.findById(1)).thenReturn(Optional.of(sampleEmployee));
            when(assetRepo.findById(10)).thenReturn(Optional.of(sampleAsset));
            when(assetRepo.save(any())).thenReturn(sampleAsset);
            when(requestRepo.save(any())).thenReturn(sampleRequest);

            var result = service.addRequest(sampleDTO);
            assertEquals("Screen not working", result.getDescription());
            verify(assetRepo).save(any(Asset.class));
            assertEquals(AssetStatus.REQUESTED, sampleAsset.getStatus());
        }

        @Test
        void testAddRequest_EmployeeNotFound() {
            when(employeeRepo.findById(1)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> service.addRequest(sampleDTO));
        }

        @Test
        void testAddRequest_AssetNotFound() {
            when(employeeRepo.findById(1)).thenReturn(Optional.of(sampleEmployee));
            when(assetRepo.findById(10)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> service.addRequest(sampleDTO));
        }

        @Test
        void testUpdateRequest_Success() {
            when(requestRepo.findById(100)).thenReturn(Optional.of(sampleRequest));
            when(requestRepo.save(any())).thenReturn(sampleRequest);

            sampleDTO.setDescription("Updated description");
            sampleDTO.setStatus("RESOLVED");

            var result = service.updateRequest(100, sampleDTO);
            assertEquals("Updated description", result.getDescription());
            assertEquals("RESOLVED", result.getStatus());
        }

        @Test
        void testUpdateRequest_NotFound() {
            when(requestRepo.findById(99)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> service.updateRequest(99, sampleDTO));
        }

        @Test
        void testDeleteRequest_Success() {
            when(requestRepo.existsById(100)).thenReturn(true);
            doNothing().when(requestRepo).deleteById(100);
            assertTrue(service.deleteRequest(100));
        }

        @Test
        void testDeleteRequest_NotFound() {
            when(requestRepo.existsById(99)).thenReturn(false);
            assertThrows(ResourceNotFoundException.class, () -> service.deleteRequest(99));
        }

        @Test
        void testGetRequestsByEmployee() {
            when(requestRepo.findByEmployeeId(1)).thenReturn(List.of(sampleRequest));
            var result = service.getRequestsByEmployee(1);
            assertEquals(1, result.size());
        }

        @Test
        void testGetRequestsByStatus_Valid() {
            when(requestRepo.findByStatus(ServiceStatus.PENDING)).thenReturn(List.of(sampleRequest));
            var result = service.getRequestsByStatus("PENDING");
            assertEquals(1, result.size());
        }

        @Test
        void testGetRequestsByStatus_Invalid() {
            assertThrows(ResourceNotFoundException.class, () -> service.getRequestsByStatus("INVALID"));
        }

        @Test
        void testCountTotalRequests() {
            when(requestRepo.countByEmployeeId(1)).thenReturn(5);
            assertEquals(5, service.countTotalRequests(1));
        }

        @Test
        void testCountPendingRequests() {
            when(requestRepo.countByEmployeeIdAndStatus(1, ServiceStatus.PENDING)).thenReturn(3);
            assertEquals(3, service.countPendingRequests(1));
        }
    }



