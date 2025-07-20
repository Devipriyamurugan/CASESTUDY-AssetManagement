package com.hexaware.assetmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.assetmanagement.entity.Asset;
import com.hexaware.assetmanagement.entity.Employee;
import com.hexaware.assetmanagement.enums.AssetStatus;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Integer>{
	
    List<Asset> findByCategoryId(Long categoryId);

    // Filter by Status (like "available" or "assigned")
    List<Asset> findByStatus(AssetStatus status);
    
//    List<Asset> findByAssignedTo(int employeeId);
    
    List<Asset> findByAssignedTo_Id(int employeeId);
    
    int countByAssignedTo_Id(int employeeId);
    
    @Query("SELECT DISTINCT a.assignedTo FROM Asset a WHERE a.status = com.hexaware.assetmanagement.enums.AssetStatus.ASSIGNED AND a.assetCondition = com.hexaware.assetmanagement.enums.AssetCondition.GOOD")
    List<Employee> findEmployeesWithAssignedAssets();
    
    @Query("SELECT COUNT(DISTINCT a.assignedTo.id) " +
    	       "FROM Asset a " +
    	       "WHERE a.status = com.hexaware.assetmanagement.enums.AssetStatus.ASSIGNED " +
    	       "AND a.assetCondition = com.hexaware.assetmanagement.enums.AssetCondition.GOOD")
    	long countDistinctEmployeesWithAssignedAssets();





}
