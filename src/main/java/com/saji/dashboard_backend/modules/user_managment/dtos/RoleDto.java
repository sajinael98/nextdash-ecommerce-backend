package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.shared.dtos.BaseDto;

import lombok.Data;

@Data
public class RoleDto extends BaseDto {
    private Long id;
    public String role;
    public boolean enabled;
}
