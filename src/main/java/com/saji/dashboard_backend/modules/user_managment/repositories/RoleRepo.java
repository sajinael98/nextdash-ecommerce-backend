package com.saji.dashboard_backend.modules.user_managment.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;

@Repository
public interface RoleRepo extends BaseRepository<Role, Long>{
    
}
