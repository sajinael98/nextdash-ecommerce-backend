package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.shared.dtos.BaseDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleDto extends BaseDto {
    @NotEmpty
    public String role;
    public boolean enabled;
}
