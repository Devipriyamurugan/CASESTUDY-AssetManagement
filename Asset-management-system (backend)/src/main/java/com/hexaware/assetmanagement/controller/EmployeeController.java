package com.hexaware.assetmanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.assetmanagement.dto.EmployeeRequestDTO;
import com.hexaware.assetmanagement.dto.EmployeeResponseDTO;
import com.hexaware.assetmanagement.serviceimpl.AssetService;
import com.hexaware.assetmanagement.serviceimpl.EmployeeService;
import com.hexaware.assetmanagement.serviceimpl.ServiceRequestService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService service;
    
    @Autowired
    AssetService a_service;
    
    @Autowired
    ServiceRequestService sr_service;

    // GET /employees - Get all employees
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> list = service.getAllEmployee();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET /employees/{id} - Get employee by ID
    
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable int id) {
        EmployeeResponseDTO dto = service.getEmployeeById(id);

        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST /addEmployee - Add new employee
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addEmployee")
    public ResponseEntity<EmployeeResponseDTO> addEmployee(@RequestBody EmployeeRequestDTO requestDto) {
        EmployeeResponseDTO dto = service.addEmployee(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Success-Message", "Employee created successfully");

        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }

    // PUT /updateEmployee/{id} - Update employee
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable int id, @RequestBody EmployeeRequestDTO requestDto) {
        try {
            EmployeeResponseDTO dto = service.updateEmployee(id, requestDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /deleteEmployee/{id} - Delete employee
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        EmployeeResponseDTO dto = service.deleteEmployee(id);
        if (dto != null) {
            return new ResponseEntity<>("Employee with ID " + id + " deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/dashboard/stats/{employeeId}")
    public ResponseEntity<Map<String, Integer>> getDashboardStats(@PathVariable int employeeId) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("myAssets", a_service.countAssetsByEmployee(employeeId));
        stats.put("pendingRequests", sr_service.countPendingRequests(employeeId));
        stats.put("totalRequests", sr_service.countTotalRequests(employeeId));
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }


}
