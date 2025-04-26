package com.saji.dashboard_backend.modules.stock_management.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.saji.dashboard_backend.shared.entites.BaseReport;

public class StockRoomSummary extends BaseReport {

    public StockRoomSummary(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<Map<String, Object>> execute(Map<String, Object> filters) {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("item", "test");
        data.add(row1);
        Map<String, Object> row2 = new HashMap<>();
        row2.put("item", "test");
        data.add(row2);
        return data;
    }

    

}
