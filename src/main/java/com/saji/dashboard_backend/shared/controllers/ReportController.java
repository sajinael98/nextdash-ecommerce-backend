package com.saji.dashboard_backend.shared.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.shared.dtos.ReportResult;
import com.saji.dashboard_backend.shared.services.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping
    public ResponseEntity<ReportResult> getReportResults(@RequestParam Map<String, Object> filters) {
        if (!filters.containsKey("reportName")) {
            throw new RuntimeException("reportName is required");
        }
        return ResponseEntity.ok().body(service.execute((String) filters.get("reportName"), filters));
    }
}
