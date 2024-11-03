package com.saji.dashboard_backend.modules.user_managment.mappers;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.RoleDto;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;

@Service
public class RoleMapper implements BaseMapper<Role, RoleDto>{

    @Override
    public Role createEntity() {
        return new Role();
    }

    @Override
    public RoleDto createEntityDto() {
        return new RoleDto();
    }
    
}
