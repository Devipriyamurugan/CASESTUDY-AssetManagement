package com.hexaware.assetmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

}
