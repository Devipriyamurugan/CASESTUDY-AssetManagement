package com.hexaware.assetmanagement.mapper;

import com.hexaware.assetmanagement.dto.AdminResponseDTO;
import com.hexaware.assetmanagement.entity.Admin;

public class AdminMapper {

    // Entity to DTO
    public static AdminResponseDTO toDTO(Admin admin) {
        if (admin == null) return null;

        return new AdminResponseDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getContactNumber(),
                admin.getDepartment(),
                admin.isActive()
        );
    }

    // DTO to Entity
    public static Admin toEntity(AdminResponseDTO dto) {
        if (dto == null) return null;

        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setContactNumber(dto.getContactNumber());
        admin.setDepartment(dto.getDepartment());
        admin.setActive(dto.isActive());

        return admin;
    }
}
