package com.hexaware.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.hexaware.assetmanagement.dao.*;
import com.hexaware.assetmanagement.dto.AssignedAssetsDTO;
import com.hexaware.assetmanagement.dto.DashboardStatsDTO;
import com.hexaware.assetmanagement.dto.EmployeeResponseDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.enums.AssetCondition;
import com.hexaware.assetmanagement.enums.AssetStatus;
import com.hexaware.assetmanagement.enums.ServiceStatus;
import com.hexaware.assetmanagement.mapper.EmployeeMapper;
import com.hexaware.assetmanagement.serviceimpl.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

    class AdminServiceTest {

        @Mock
        private AdminRepository adminRepo;

        @Mock
        private EmployeeRepository employeeRepo;

        @Mock
        private AssetRepository assetRepo;

        @Mock
        private ServiceRequestRepository serviceRequestRepo;

        @InjectMocks
        private AdminService adminService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetAllEmployeeDetails() {
            Employee emp = new Employee();
            emp.setId(1);
            emp.setName("Nandy");
            emp.setEmail("nandy@test.com");

            when(employeeRepo.findAll()).thenReturn(List.of(emp));

            List<EmployeeResponseDTO> result = adminService.getAllEmployeeDetails();
            assertEquals(1, result.size());
            assertEquals("Nandy", result.get(0).getName());
        }

        @Test
        void testUpdateEmployeeStatus() {
            Employee emp = new Employee();
            emp.setId(1);
            emp.setActive(false);

            when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
            when(employeeRepo.save(any(Employee.class))).thenReturn(emp);

            EmployeeResponseDTO dto = adminService.updateEmployeeStatus(1, true);
            assertTrue(dto.isActive());
        }

        @Test
        void testDeleteEmployeeById() {
            Employee emp = new Employee();
            emp.setId(1);

            when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
            doNothing().when(employeeRepo).deleteById(1);

            boolean result = adminService.deleteEmployeeById(1);
            assertTrue(result);
        }

        @Test
        void testGetDashboardStats() {
            when(employeeRepo.count()).thenReturn(10L);
            when(assetRepo.count()).thenReturn(20L);
            when(serviceRequestRepo.count()).thenReturn(5L);
            when(serviceRequestRepo.countByStatus(ServiceStatus.PENDING)).thenReturn(2L);

            DashboardStatsDTO stats = adminService.getDashboardStats();
            assertEquals(10L, stats.getTotalEmployees());
            assertEquals(20L, stats.getTotalAssets());
            assertEquals(5L, stats.getTotalServiceRequests());
            assertEquals(2L, stats.getPendingServiceRequests());
        }

        @Test
        void testGetAssignedAssets() {
            Employee emp = new Employee();
            emp.setName("Nandy");
            emp.setEmail("nandy@test.com");
            emp.setDepartment("Dev");
            emp.setContactNumber("123456");
            emp.setActive(true);

            Asset asset = new Asset();
            asset.setName("Laptop");
            asset.setStatus(AssetStatus.ASSIGNED);
            asset.setAssetCondition(AssetCondition.GOOD);
            emp.setAssets(List.of(asset));

            when(assetRepo.findEmployeesWithAssignedAssets()).thenReturn(List.of(emp));

            List<AssignedAssetsDTO> result = adminService.getAssignedAssets();
            assertEquals(1, result.size());
            assertEquals("Laptop", result.get(0).getAssetNames().get(0));

        }

        @Test
        void testGetAssignedAssetsCount() {
            when(assetRepo.countDistinctEmployeesWithAssignedAssets()).thenReturn(3L);
            assertEquals(3L, adminService.getAssignedAssetsCount());
        }

        @Test
        void testGetTotalEmployeesCount() {
            when(employeeRepo.count()).thenReturn(8L);
            assertEquals(8L, adminService.getTotalEmployeesCount());
        }

        @Test
        void testGetActiveEmployeesCount() {
            when(employeeRepo.countByActive(true)).thenReturn(6L);
            assertEquals(6L, adminService.getActiveEmployeesCount());
        }
    }

