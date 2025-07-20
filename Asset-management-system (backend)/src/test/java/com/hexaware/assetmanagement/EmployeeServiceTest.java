package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dto.EmployeeRequestDTO;
import com.hexaware.assetmanagement.dto.EmployeeResponseDTO;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.EmployeeMapper;
import com.hexaware.assetmanagement.serviceimpl.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class EmployeeServiceTest {

        @InjectMocks
        private EmployeeService employeeService;

        @Mock
        private EmployeeRepository repo;

        private Employee sampleEmployee;
        private EmployeeRequestDTO sampleRequestDTO;
        private EmployeeResponseDTO sampleResponseDTO;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);

            sampleEmployee = new Employee();
            sampleEmployee.setId(1);
            sampleEmployee.setName("John Doe");
            sampleEmployee.setEmail("john.doe@example.com");
            sampleEmployee.setPassword("password");
            sampleEmployee.setDepartment("IT");
            sampleEmployee.setDesignation("Developer");
            sampleEmployee.setContactNumber("1234567890");
            sampleEmployee.setJoinDate(LocalDate.now());
            sampleEmployee.setActive(true);

            sampleRequestDTO = new EmployeeRequestDTO();
            sampleRequestDTO.setName("John Doe");
            sampleRequestDTO.setEmail("john.doe@example.com");
            sampleRequestDTO.setPassword("password");
            sampleRequestDTO.setDepartment("IT");
            sampleRequestDTO.setDesignation("Developer");
            sampleRequestDTO.setContactNumber("1234567890");
            sampleRequestDTO.setJoinDate(LocalDate.now());
            sampleRequestDTO.setActive(true);

            sampleResponseDTO = new EmployeeResponseDTO();
            sampleResponseDTO.setId(1);
            sampleResponseDTO.setName("John Doe");
            sampleResponseDTO.setEmail("john.doe@example.com");
        }

        @Test
        void testGetAllEmployee() {
            when(repo.findAll()).thenReturn(List.of(sampleEmployee));
            List<EmployeeResponseDTO> result = employeeService.getAllEmployee();
            assertEquals(1, result.size());
            verify(repo).findAll();
        }

        @Test
        void testGetEmployeeById_Found() {
            when(repo.findById(1)).thenReturn(Optional.of(sampleEmployee));
            EmployeeResponseDTO result = employeeService.getEmployeeById(1);
            assertEquals("John Doe", result.getName());
            verify(repo).findById(1);
        }

        @Test
        void testGetEmployeeById_NotFound() {
            when(repo.findById(999)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(999));
        }

        @Test
        void testAddEmployee() {
            when(repo.save(any(Employee.class))).thenReturn(sampleEmployee);
            EmployeeResponseDTO result = employeeService.addEmployee(sampleRequestDTO);
            assertEquals("John Doe", result.getName());
            verify(repo).save(any(Employee.class));
        }

        @Test
        void testUpdateEmployee_Success() {
            when(repo.findById(1)).thenReturn(Optional.of(sampleEmployee));
            when(repo.save(any(Employee.class))).thenReturn(sampleEmployee);

            sampleRequestDTO.setName("Jane Doe");
            sampleRequestDTO.setEmail("jane.doe@example.com");

            EmployeeResponseDTO result = employeeService.updateEmployee(1, sampleRequestDTO);
            assertNotNull(result);
            assertEquals("Jane Doe", result.getName());
            verify(repo).findById(1);
            verify(repo).save(any(Employee.class));
        }

        @Test
        void testUpdateEmployee_NotFound() {
            when(repo.findById(999)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(999, sampleRequestDTO));
        }

        @Test
        void testDeleteEmployee_Success() {
            when(repo.findById(1)).thenReturn(Optional.of(sampleEmployee));
            doNothing().when(repo).deleteById(1);

            EmployeeResponseDTO result = employeeService.deleteEmployee(1);
            assertEquals("John Doe", result.getName());
            verify(repo).deleteById(1);
        }

        @Test
        void testDeleteEmployee_NotFound() {
            when(repo.findById(99)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(99));
        }
    }


