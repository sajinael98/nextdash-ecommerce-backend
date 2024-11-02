package com.saji.dashboard_backend.modules.user_managment.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.RoleResponse;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;

@Service
public class RoleMapper implements BaseMapper<Role> {

    @Override
    public RoleResponse convertEntityToResponse(Role entity) {
        RoleResponse res = new RoleResponse();
        BeanUtils.copyProperties(entity, res);
        return res;
    }
}
