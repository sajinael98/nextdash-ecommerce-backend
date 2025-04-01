package com.saji.dashboard_backend.system.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceDbService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getResourceValues(String resource, List<String> columns, List<String> conditions,
            MapSqlParameterSource params) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append(String.join(", ", columns));
        queryBuilder.append(" FROM ").append(resource);

        if (conditions != null && !conditions.isEmpty()) {
            queryBuilder.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        try {
            return namedParameterJdbcTemplate.queryForList(queryBuilder.toString(), params);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error executing query: " + queryBuilder.toString(), e);
        }
    }
}
