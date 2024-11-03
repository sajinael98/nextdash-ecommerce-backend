package com.saji.dashboard_backend.modules.user_managment.mappers;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;

@Service
public class PermissionMapper implements BaseMapper<Permission, PermissionDto> {

    @Override
    public Permission createEntity() {
        return new Permission();
    }

    @Override
    public PermissionDto createEntityDto() {
        return new PermissionDto();
    }

}
