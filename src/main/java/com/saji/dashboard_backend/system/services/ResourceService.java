package com.saji.dashboard_backend.system.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.entities.Permission;

@Service
public class ResourceService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    public List<String> getResources() {
        String sql = """
                SELECT
                    TABLE_NAME
                FROM
                    information_schema.TABLES
                WHERE
                    TABLE_SCHEMA = ? AND TABLE_TYPE = 'BASE TABLE' AND TABLE_NAME LIKE 'res_%';
                """;

        List<String> tables = jdbcTemplate.queryForList(sql, String.class, getDatabaseName());

        List<String> formattedTables = tables.stream()
                .map(table -> {
                    // Remove the "res_" prefix and split the remaining string
                    String[] splitTable = table.substring(4).split("_");
                    return Arrays.stream(splitTable)
                            .map(temp -> Character.toUpperCase(temp.charAt(0)) + temp.substring(1))
                            .collect(Collectors.joining(" "));
                })
                .collect(Collectors.toList());

        return formattedTables;
    }

    public List<Permission> getResourcePermissions(String resource) {
        String sql = "SELECT * FROM role_permissions WHERE resource = ?";

        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Permission.class), resource);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch resource permissions", e);
        }
    }

    private String getDatabaseName() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
