package com.saji.dashboard_backend.modules.user_managment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.user_managment.entities.Permission;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long>{
    
}
