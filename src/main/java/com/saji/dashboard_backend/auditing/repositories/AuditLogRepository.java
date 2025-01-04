package com.saji.dashboard_backend.auditing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.auditing.entities.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByResourceAndResourceId(@Param("resource") String resource, @Param("resourceId") String resourceId);
}
