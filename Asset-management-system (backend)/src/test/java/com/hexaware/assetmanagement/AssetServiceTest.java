package com.hexaware.assetmanagement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.hexaware.assetmanagement.dao.*;
import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Category;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.enums.AssetCondition;
import com.hexaware.assetmanagement.enums.AssetStatus;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.mapper.AssetMapper;
import com.hexaware.assetmanagement.serviceimpl.AssetService;

    class AssetServiceTest {

        @Mock
        private AssetRepository repo;

        @Mock
        private CategoryRepository categoryRepo;

        @Mock
        private EmployeeRepository employeeRepo;

        @InjectMocks
        private AssetService assetService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testGetAllAssets() {
            Asset asset = new Asset();
            asset.setId(1);
            asset.setName("Monitor");

            when(repo.findAll()).thenReturn(List.of(asset));

            List<AssetDTO> result = assetService.getAllAssets();
            assertEquals(1, result.size());
            assertEquals("Monitor", result.get(0).getName());
        }

        @Test
        void testGetAssetById_Success() {
            Asset asset = new Asset();
            asset.setId(1);
            asset.setName("Keyboard");

            when(repo.findById(1)).thenReturn(Optional.of(asset));

            AssetDTO result = assetService.getAssetById(1);
            assertEquals("Keyboard", result.getName());
        }

        @Test
        void testGetAssetById_NotFound() {
            when(repo.findById(99)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                assetService.getAssetById(99);
            });
        }

        @Test
        void testAddAsset_Success() {
            AssetDTO dto = new AssetDTO();
            dto.setName("Laptop");
            dto.setCategoryName("Electronics");

            Category category = new Category();
            category.setName("Electronics");

            Asset asset = new Asset();
            asset.setName("Laptop");

            when(categoryRepo.findByName("Electronics")).thenReturn(category);
            when(repo.save(any(Asset.class))).thenReturn(asset);

            AssetDTO result = assetService.addAsset(dto);
            assertEquals("Laptop", result.getName());
        }

        @Test
        void testDeleteAsset() {
            Asset asset = new Asset();
            asset.setId(1);
            asset.setName("Mouse");

            when(repo.findById(1)).thenReturn(Optional.of(asset));
            doNothing().when(repo).deleteById(1);

            AssetDTO result = assetService.deleteAsset(1);
            assertEquals("Mouse", result.getName());
        }

        @Test
        void testGetAssetsByCategory() {
            Asset asset = new Asset();
            asset.setId(2);
            asset.setName("Tablet");

            when(repo.findByCategoryId(10L)).thenReturn(List.of(asset));

            List<AssetDTO> result = assetService.getAssetsByCategory(10L);
            assertEquals("Tablet", result.get(0).getName());
        }

        @Test
        void testGetAssetsByStatus() {
            Asset asset = new Asset();
            asset.setStatus(AssetStatus.ASSIGNED);
            asset.setName("Speaker");

            when(repo.findByStatus(AssetStatus.ASSIGNED)).thenReturn(List.of(asset));

            List<AssetDTO> result = assetService.getAssetsByStatus("ASSIGNED");
            assertEquals("Speaker", result.get(0).getName());
        }

        @Test
        void testGetAssetsAssignedToEmployee() {
            Asset asset = new Asset();
            asset.setName("Phone");

            when(repo.findByAssignedTo_Id(5)).thenReturn(List.of(asset));

            List<AssetDTO> result = assetService.getAssetsAssignedToEmployee(5);
            assertEquals("Phone", result.get(0).getName());
        }

        @Test
        void testCountAssetsByEmployee() {
            when(repo.countByAssignedTo_Id(4)).thenReturn(3);
            int count = assetService.countAssetsByEmployee(4);
            assertEquals(3, count);
        }
    }

