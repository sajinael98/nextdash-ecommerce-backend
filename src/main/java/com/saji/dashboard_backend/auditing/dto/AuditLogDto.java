package com.saji.dashboard_backend.auditing.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditLogDto {
    private String resource;
    private String resourceId;
    private String action;
    private String user;
    private LocalDateTime date;
}
