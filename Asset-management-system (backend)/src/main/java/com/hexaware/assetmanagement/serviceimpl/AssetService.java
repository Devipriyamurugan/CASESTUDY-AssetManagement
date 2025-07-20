package com.hexaware.assetmanagement.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dao.AssetRepository;
import com.hexaware.assetmanagement.dao.CategoryRepository;
import com.hexaware.assetmanagement.dao.EmployeeRepository;
import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Category;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.enums.AssetCondition;
import com.hexaware.assetmanagement.enums.AssetStatus;
import com.hexaware.assetmanagement.exception.InvalidInputException;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.AssetMapper;

@Service
public class AssetService {

    @Autowired
    private AssetRepository repo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    // GET all assets
    public List<AssetDTO> getAllAssets() {
        return repo.findAll()
                   .stream()
                   .map(AssetMapper::toDTO)
                   .collect(Collectors.toList());
    }

    // GET by ID
    public AssetDTO getAssetById(int id) {
        return repo.findById(id)
                   .map(AssetMapper::toDTO)
                   .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
    }

    // ADD new asset
    public AssetDTO addAsset(AssetDTO dto) {
        // Find category by name
        Category category = categoryRepo.findByName(dto.getCategoryName());
        if (category == null) {
            throw new ResourceNotFoundException("Category not found: " + dto.getCategoryName());
        }

        // AssignedTo (optional)
        Employee assignedTo = null;
        if (dto.getAssignedToName() != null && !dto.getAssignedToName().isEmpty()) {
            assignedTo = employeeRepo.findByName(dto.getAssignedToName());
            if (assignedTo == null) {
                throw new ResourceNotFoundException("Assigned employee not found: " + dto.getAssignedToName());
            }
        }

        Asset asset = AssetMapper.toEntity(dto, category, assignedTo);
        Asset saved = repo.save(asset);
        return AssetMapper.toDTO(saved);
    }

    // UPDATE asset
//    public AssetDTO updateAsset(int id, AssetDTO dto) {
//        Asset existing = repo.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
//
//        existing.setName(dto.getName());
//        existing.setAssetNumber(dto.getAssetNumber());
//
//        if (dto.getStatus() != null) {
//            try {
//                existing.setStatus(AssetStatus.valueOf(dto.getStatus().toUpperCase()));
//            } catch (IllegalArgumentException e) {
//                throw new InvalidInputException("Invalid asset status: " + dto.getStatus());
//            }
//        }
//
//        if (dto.getAssetCondition() != null) {
//            try {
//                existing.setAssetCondition(AssetCondition.valueOf(dto.getAssetCondition().toUpperCase()));
//            } catch (IllegalArgumentException e) {
//                throw new InvalidInputException("Invalid asset condition: " + dto.getAssetCondition());
//            }
//        }
//
//        existing.setPurchasedDate(dto.getPurchasedDate());
//
//        // Update category if provided
//        if (dto.getCategoryName() != null) {
//            Category category = categoryRepo.findByName(dto.getCategoryName());
//            if (category == null) {
//                throw new ResourceNotFoundException("Category not found: " + dto.getCategoryName());
//            }
//            existing.setCategory(category);
//        }
//
//        // Update assignedTo if provided
//        if (dto.getAssignedToName() != null) {
//            Employee assignedTo = employeeRepo.findByName(dto.getAssignedToName());
//            if (assignedTo == null) {
//                throw new ResourceNotFoundException("Assigned employee not found: " + dto.getAssignedToName());
//            }
//            existing.setAssignedTo(assignedTo);
//        }
//
//        return AssetMapper.toDTO(repo.save(existing));
//    }
      
    public AssetDTO updateAsset(int id, AssetDTO dto) {
        Asset existing = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        // Update basic fields
        existing.setName(dto.getName());
        existing.setAssetNumber(dto.getAssetNumber());

        // Update status if valid
        if (dto.getStatus() != null) {
            try {
                existing.setStatus(AssetStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid asset status: " + dto.getStatus());
            }
        }

        // Update asset condition if valid
        if (dto.getAssetCondition() != null) {
            try {
                existing.setAssetCondition(AssetCondition.valueOf(dto.getAssetCondition().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid asset condition: " + dto.getAssetCondition());
            }
        }

        // Update purchase date
        if (dto.getPurchasedDate() != null) {
            existing.setPurchasedDate(dto.getPurchasedDate());
        }

        // Update category if provided
        if (dto.getCategoryName() != null) {
            Category category = categoryRepo.findByName(dto.getCategoryName());
            if (category == null) {
                throw new ResourceNotFoundException("Category not found: " + dto.getCategoryName());
            }
            existing.setCategory(category);
        }

        // ✅ Update assignedTo using assignedToId (preferred)
        if (dto.getAssignedToId() != 0) {
            Optional<Employee> assignedToOpt = employeeRepo.findById(dto.getAssignedToId());
            if (assignedToOpt.isEmpty()) {
                throw new ResourceNotFoundException("Assigned employee not found with ID: " + dto.getAssignedToId());
            }
            existing.setAssignedTo(assignedToOpt.get());

        // Fallback: update using name if ID not given
        } else if (dto.getAssignedToName() != null && !dto.getAssignedToName().isEmpty()) {
            Employee assignedTo = employeeRepo.findByName(dto.getAssignedToName());
            if (assignedTo == null) {
                throw new ResourceNotFoundException("Assigned employee not found: " + dto.getAssignedToName());
            }
            existing.setAssignedTo(assignedTo);
        }

        // Save and return updated asset
        Asset updated = repo.save(existing);
        return AssetMapper.toDTO(updated);
    }

    // DELETE asset
    public AssetDTO deleteAsset(int id) {
        Asset asset = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        repo.deleteById(id);
        return AssetMapper.toDTO(asset);
    }

    // FILTER by category
    public List<AssetDTO> getAssetsByCategory(Long cid) {
        return repo.findByCategoryId(cid)
                   .stream()
                   .map(AssetMapper::toDTO)
                   .collect(Collectors.toList());
    }

    // FILTER by status
    public List<AssetDTO> getAssetsByStatus(String status) {
        AssetStatus statusEnum;
        try {
            statusEnum = AssetStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid asset status: " + status);
        }

        return repo.findByStatus(statusEnum)
                   .stream()
                   .map(AssetMapper::toDTO)
                   .collect(Collectors.toList());
    }
    
    public List<AssetDTO> getAssetsAssignedToEmployee(int employeeId) {
        List<Asset> assignedAssets = repo.findByAssignedTo_Id(employeeId);
        return assignedAssets.stream()
            .map(AssetMapper::toDTO)
            .collect(Collectors.toList());
    }
    
 // Count how many assets are assigned to a specific employee
    public int countAssetsByEmployee(int employeeId) {
        return repo.countByAssignedTo_Id(employeeId);  // assumes this exists in AssetRepository
    }


}
