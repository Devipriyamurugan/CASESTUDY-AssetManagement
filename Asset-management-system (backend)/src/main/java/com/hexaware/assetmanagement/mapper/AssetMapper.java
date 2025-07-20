package com.hexaware.assetmanagement.mapper;

import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Category;
import com.hexaware.assetmanagement.entity.Employee;

public class AssetMapper {

    // Convert Entity → DTO
	public static AssetDTO toDTO(Asset asset) {
	    if (asset == null) return null;

	    AssetDTO dto = new AssetDTO(
	    	    asset.getId(),
	    	    asset.getName(),
	    	    asset.getAssetNumber(),
	    	    asset.getStatus() != null ? asset.getStatus().name() : null,
	    	    asset.getAssetCondition() != null ? asset.getAssetCondition().name() : null,
	    	    asset.getPurchasedDate(),
	    	    asset.getCategory() != null ? asset.getCategory().getName() : null,
	    	    asset.getAssignedTo() != null ? asset.getAssignedTo().getName() : null,
	    	    asset.getAssignedTo() != null ? asset.getAssignedTo().getId() : 0
	    	);

	    // ✅ Add this line to include the employee ID
//	    dto.setAssignedToId(asset.getAssignedTo() != null ? asset.getAssignedTo().getId() : 0);

	    return dto;
	}


    // Convert DTO -> Entity
    public static Asset toEntity(AssetDTO dto, Category category, Employee assignedTo) {
        if (dto == null) return null;

        Asset asset = new Asset();
        asset.setId(dto.getId());
        asset.setName(dto.getName());
        asset.setAssetNumber(dto.getAssetNumber());

        // Enums from string
        if (dto.getStatus() != null)
            asset.setStatus(Enum.valueOf(com.hexaware.assetmanagement.enums.AssetStatus.class, dto.getStatus()));

        if (dto.getAssetCondition() != null)
            asset.setAssetCondition(Enum.valueOf(com.hexaware.assetmanagement.enums.AssetCondition.class, dto.getAssetCondition()));

        asset.setPurchasedDate(dto.getPurchasedDate());
        asset.setCategory(category);          
        asset.setAssignedTo(assignedTo);   

        return asset;
    }
}
