package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.shared.dtos.BaseDto;

import lombok.Data;

@Data
public class PermissionDto extends BaseDto {
    private Long id;
    private String entity;
    private boolean createR;
    private boolean readR;
    private boolean editR;
    private boolean deleteR;
}
