package com.saji.dashboard_backend.shared.services;

import java.util.Map;

import com.saji.dashboard_backend.shared.dtos.ReportResult;

public interface ReportService {
    ReportResult execute(String reportName, Map<String, Object> filters);
}
