package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.shared.dtos.BaseRequest;
import com.saji.dashboard_backend.shared.dtos.BaseResponse;

import lombok.Data;

@Data
public class PermissionDto implements BaseRequest, BaseResponse {
    private String entity;
    private boolean createR;
    private boolean readR;
    private boolean editR;
    private boolean deleteR;
}
