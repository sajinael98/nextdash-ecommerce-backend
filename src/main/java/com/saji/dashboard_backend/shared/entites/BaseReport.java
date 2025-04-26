package com.saji.dashboard_backend.shared.entites;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.saji.dashboard_backend.shared.dtos.ReportColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseReport {
    private JdbcTemplate jdbcTemplate;

    public abstract List<Map<String, Object>> execute(Map<String, Object> filters);
}