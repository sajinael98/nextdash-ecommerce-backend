package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.modules.user_managment.entities.Permission;

import lombok.Data;

@Data
public class PermissionDto extends Permission {
    private long roleId;
    private String role;
}
