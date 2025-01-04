package com.saji.dashboard_backend.auditing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.auditing.entities.AuditLog;
import com.saji.dashboard_backend.auditing.services.AuditLogService;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogsController {
    @Autowired
    private AuditLogService service;

    @PostMapping
    public ResponseEntity<Void> createLog(@RequestBody AuditLog log) {
        service.createLog(log);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getLogsByResourceAndId(@RequestParam(required = true) String resource,
            @RequestParam(required = true) String resourceId) {
        return ResponseEntity.ok().body(service.getLogsByResourceAndId(resource, resourceId));
    }
}
