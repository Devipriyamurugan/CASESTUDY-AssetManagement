package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.dao.CategoryRepository;
import com.hexaware.assetmanagement.dto.CategoryDTO;
import com.hexaware.assetmanagement.entity.Category;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.CategoryMapper;
import com.hexaware.assetmanagement.serviceimpl.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class CategoryServiceTest {

        @InjectMocks
        private CategoryService categoryService;

        @Mock
        private CategoryRepository categoryRepo;

        private Category sampleCategory;
        private CategoryDTO sampleDTO;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);

            sampleCategory = new Category();
            sampleCategory.setId(1);
            sampleCategory.setName("Electronics");
            sampleCategory.setDescription("Electronic Items");

            sampleDTO = new CategoryDTO();
            sampleDTO.setId(1);
            sampleDTO.setName("Electronics");
            sampleDTO.setDescription("Electronic Items");
        }

        @Test
        void testGetAllCategories() {
            when(categoryRepo.findAll()).thenReturn(List.of(sampleCategory));

            List<CategoryDTO> result = categoryService.getAllCategories();

            assertEquals(1, result.size());
            assertEquals("Electronics", result.get(0).getName());
            verify(categoryRepo, times(1)).findAll();
        }

        @Test
        void testGetCategoryById_Found() {
            when(categoryRepo.findById(1)).thenReturn(Optional.of(sampleCategory));

            CategoryDTO result = categoryService.getCategoryById(1);

            assertEquals("Electronics", result.getName());
            verify(categoryRepo).findById(1);
        }

        @Test
        void testGetCategoryById_NotFound() {
            when(categoryRepo.findById(99)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(99));
        }

        @Test
        void testAddCategory() {
            when(categoryRepo.save(any(Category.class))).thenReturn(sampleCategory);

            CategoryDTO result = categoryService.addCategory(sampleDTO);

            assertNotNull(result);
            assertEquals("Electronics", result.getName());
            verify(categoryRepo).save(any(Category.class));
        }

        @Test
        void testUpdateCategory_Success() {
            Category updated = new Category();
            updated.setId(1);
            updated.setName("Updated");
            updated.setDescription("Updated Desc");

            sampleDTO.setName("Updated");
            sampleDTO.setDescription("Updated Desc");

            when(categoryRepo.findById(1)).thenReturn(Optional.of(sampleCategory));
            when(categoryRepo.save(any(Category.class))).thenReturn(updated);

            CategoryDTO result = categoryService.updateCategory(1, sampleDTO);

            assertEquals("Updated", result.getName());
            verify(categoryRepo).findById(1);
            verify(categoryRepo).save(any(Category.class));
        }

        @Test
        void testUpdateCategory_NotFound() {
            when(categoryRepo.findById(100)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class,
                    () -> categoryService.updateCategory(100, sampleDTO));
        }

        @Test
        void testDeleteCategory_Success() {
            when(categoryRepo.existsById(1)).thenReturn(true);
            doNothing().when(categoryRepo).deleteById(1);

            boolean result = categoryService.deleteCategory(1);

            assertTrue(result);
            verify(categoryRepo).deleteById(1);
        }

        @Test
        void testDeleteCategory_NotFound() {
            when(categoryRepo.existsById(2)).thenReturn(false);

            assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(2));
        }
    }


