package com.hexaware.assetmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

	Category findByName(String categoryName);

}
