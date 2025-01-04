package com.saji.dashboard_backend.auditing.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.auditing.entities.AuditLog;
import com.saji.dashboard_backend.auditing.repositories.AuditLogRepository;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository repo;

    public void createLog(AuditLog log) {
        repo.save(log);
    }

    public List<AuditLog> getLogsByResourceAndId(String resource, String resourceId) {
        return repo.findByResourceAndResourceId(resource, resourceId);
    }
}
