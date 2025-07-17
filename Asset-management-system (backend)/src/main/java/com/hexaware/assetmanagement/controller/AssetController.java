package com.hexaware.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.serviceimpl.AssetService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> list = service.getAllAssets();
        return list.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAssetById(@PathVariable int id) {
        AssetDTO dto = service.getAssetById(id);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ✅ Restricted to ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AssetDTO> addAsset(@RequestBody AssetDTO requestDto) {
        AssetDTO dto = service.addAsset(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Success-Message", "Asset created successfully");
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(@PathVariable int id, @RequestBody AssetDTO requestDto) {
        try {
            AssetDTO dto = service.updateAsset(id, requestDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable int id) {
        AssetDTO dto = service.deleteAsset(id);
        return dto != null
            ? new ResponseEntity<>("Asset with ID " + id + " deleted successfully", HttpStatus.OK)
            : new ResponseEntity<>("Asset not found with ID " + id, HttpStatus.NOT_FOUND);
    }

    // ✅ Filtering can be allowed to both roles
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/category/{cid}")
    public ResponseEntity<List<AssetDTO>> getAssetsByCategory(@PathVariable Long cid) {
        List<AssetDTO> assets = service.getAssetsByCategory(cid);
        return assets.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetDTO>> getAssetsByStatus(@PathVariable String status) {
        List<AssetDTO> assets = service.getAssetsByStatus(status);
        return assets.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    //newly added  --> assigned assets
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @GetMapping("/assigned/{employeeId}")
    public ResponseEntity<List<AssetDTO>> getAssignedAssets(@PathVariable int employeeId) {
        List<AssetDTO> assets = service.getAssetsAssignedToEmployee(employeeId);
        return assets.isEmpty() 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(assets, HttpStatus.OK);
    }

}
