package com.saji.dashboard_backend.shared.dtos;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportResult {
    private List<Map<String, Object>> data;
    private long total;
}
