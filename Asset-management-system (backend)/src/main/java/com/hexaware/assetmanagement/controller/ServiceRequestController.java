package com.hexaware.assetmanagement.controller;

import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.serviceimpl.ServiceRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService service;

    // GET - list all service requests
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ServiceRequestDTO>> getAllRequests() {
        List<ServiceRequestDTO> list = service.getAllRequests();
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                              : new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET - request by ID
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> getRequestById(@PathVariable int id) {
        ServiceRequestDTO dto = service.getRequestById(id);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK)
                           : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST - create new service request
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<ServiceRequestDTO> addRequest(@RequestBody ServiceRequestDTO dto) {
        ServiceRequestDTO created = service.addRequest(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT - update request
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> updateRequest(@PathVariable int id, @RequestBody ServiceRequestDTO dto) {
        try {
            ServiceRequestDTO updated = service.updateRequest(id, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - remove service request
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable int id) {
        boolean deleted = service.deleteRequest(id);
        return deleted ? new ResponseEntity<>("Request deleted successfully", HttpStatus.OK)
                       : new ResponseEntity<>("Request not found", HttpStatus.NOT_FOUND);
    }

    // GET - all requests by employee ID
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/employee/{eid}")
    public ResponseEntity<List<ServiceRequestDTO>> getRequestsByEmployee(@PathVariable int eid) {
        List<ServiceRequestDTO> list = service.getRequestsByEmployee(eid);
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                              : new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET - filter requests by status
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ServiceRequestDTO>> getRequestsByStatus(@PathVariable String status) {
        List<ServiceRequestDTO> list = service.getRequestsByStatus(status);
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                              : new ResponseEntity<>(list, HttpStatus.OK);
    }
    
//    @GetMapping("/filter")
//    public ResponseEntity<List<ServiceRequestDTO>> filterRequests(
//        @RequestParam(required = false) String issueType,
//        @RequestParam(required = false) String status
//    ) {
//        List<ServiceRequestDTO> list = service.filterRequests(issueType, status);
//        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
//                              : new ResponseEntity<>(list, HttpStatus.OK);
//    }

}


