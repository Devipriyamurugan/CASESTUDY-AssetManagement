package com.hexaware.assetmanagement.mapper;

import com.hexaware.assetmanagement.dto.CategoryDTO;
import com.hexaware.assetmanagement.entity.Category;

public class CategoryMapper {

    // Entity to DTO
    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        return new CategoryDTO(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }

    // DTO to Entity
    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId()); // Optional: can skip if ID is auto-generated
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
