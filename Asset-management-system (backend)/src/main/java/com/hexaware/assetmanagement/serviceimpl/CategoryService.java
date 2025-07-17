package com.hexaware.assetmanagement.serviceimpl;

import com.hexaware.assetmanagement.dto.CategoryDTO;
import com.hexaware.assetmanagement.entity.Category;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.CategoryMapper;
import com.hexaware.assetmanagement.dao.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    // Get all categories
    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get one category by ID
    public CategoryDTO getCategoryById(int id) {
        return categoryRepo.findById(id)
                .map(CategoryMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    // Add new category
    public CategoryDTO addCategory(CategoryDTO dto) {
        Category category = CategoryMapper.toEntity(dto);
        Category saved = categoryRepo.save(category);
        return CategoryMapper.toDTO(saved);
    }

    // Update existing category
    public CategoryDTO updateCategory(int id, CategoryDTO dto) {
        Category existing = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        return CategoryMapper.toDTO(categoryRepo.save(existing));
    }

    // Delete category by ID
    public boolean deleteCategory(int id) {
        if (!categoryRepo.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        categoryRepo.deleteById(id);
        return true;
    }
}
