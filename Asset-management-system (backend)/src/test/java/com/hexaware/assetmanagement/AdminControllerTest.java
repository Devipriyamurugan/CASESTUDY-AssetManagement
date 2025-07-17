package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.controller.AdminController;
import com.hexaware.assetmanagement.dto.*;
import com.hexaware.assetmanagement.serviceimpl.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService service;

    @Test
    void testGetAllEmployees_ReturnsList() throws Exception {
        List<EmployeeResponseDTO> employees = Arrays.asList(new EmployeeResponseDTO(), new EmployeeResponseDTO());
        when(service.getAllEmployeeDetails()).thenReturn(employees);

        mockMvc.perform(get("/admin/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetAllEmployees_ReturnsNoContent() throws Exception {
        when(service.getAllEmployeeDetails()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/employees"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateEmployeeStatus_Success() throws Exception {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        when(service.updateEmployeeStatus(1, true)).thenReturn(dto);

        mockMvc.perform(put("/admin/employee/1/status/true"))
                .andExpect(status().isFound());
    }

    @Test
    void testUpdateEmployeeStatus_NotFound() throws Exception {
        when(service.updateEmployeeStatus(1, true)).thenThrow(new RuntimeException("Not Found"));

        mockMvc.perform(put("/admin/employee/1/status/true"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEmployee_Success() throws Exception {
        when(service.deleteEmployeeById(1)).thenReturn(true);

        mockMvc.perform(delete("/admin/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee with ID 1 deleted successfully"));
    }

    @Test
    void testDeleteEmployee_NotFound() throws Exception {
        when(service.deleteEmployeeById(1)).thenReturn(false);

        mockMvc.perform(delete("/admin/employee/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee not found with ID 1"));
    }

    @Test
    void testGetDashboardStats() throws Exception {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        when(service.getDashboardStats()).thenReturn(stats);

        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAssignedAssets_ReturnsList() throws Exception {
        List<AssignedAssetsDTO> list = Arrays.asList(new AssignedAssetsDTO());
        when(service.getAssignedAssets()).thenReturn(list);

        mockMvc.perform(get("/api/admin/assigned-assets"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAssignedAssets_ReturnsNoContent() throws Exception {
        when(service.getAssignedAssets()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/admin/assigned-assets"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAssignedAssetsCount() throws Exception {
        when(service.getAssignedAssetsCount()).thenReturn(5L);

        mockMvc.perform(get("/api/admin/assigned-assets/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testGetTotalEmployees() throws Exception {
        when(service.getTotalEmployeesCount()).thenReturn(10L);

        mockMvc.perform(get("/api/admin/employees/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testGetActiveEmployees() throws Exception {
        when(service.getActiveEmployeesCount()).thenReturn(8L);

        mockMvc.perform(get("/api/admin/employees/active-count"))
                .andExpect(status().isOk())
                .andExpect(content().string("8"));
    }
}
