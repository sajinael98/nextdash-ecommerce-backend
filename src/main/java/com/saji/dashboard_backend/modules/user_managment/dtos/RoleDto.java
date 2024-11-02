package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.shared.dtos.BaseRequest;
import com.saji.dashboard_backend.shared.dtos.BaseResponse;

import lombok.Data;

@Data
public class RoleDto implements BaseRequest, BaseResponse {
    public String role;
    public boolean enabled;
}
