package com.saji.dashboard_backend.shared.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.shared.dtos.ReportResult;
import com.saji.dashboard_backend.shared.entites.BaseReport;

@Service
public class ReportServiceImpl implements ReportService {

    private final JdbcTemplate jdbcTemplate;

    public ReportServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReportResult execute(String reportName, Map<String, Object> filters) {
        BaseReport report = loadReport(reportName);
        if (Objects.isNull(report)) {
            throw new RuntimeException("Report: " + reportName + " is not found");
        }

        List<Map<String, Object>> data = report.execute(filters);
        return new ReportResult(data, data.size());
    }

    private BaseReport loadReport(String reportName) {
        String basePackage = "com.saji.dashboard_backend.modules";

        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends BaseReport>> reportClasses = reflections.getSubTypesOf(BaseReport.class);

        for (Class<? extends BaseReport> reportClass : reportClasses) {
            if (reportClass.getSimpleName().equalsIgnoreCase(reportName)) {
                try {
                    return reportClass.getDeclaredConstructor(JdbcTemplate.class)
                            .newInstance(this.jdbcTemplate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
