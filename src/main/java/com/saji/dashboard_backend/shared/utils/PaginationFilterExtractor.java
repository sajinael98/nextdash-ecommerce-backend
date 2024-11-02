package com.saji.dashboard_backend.shared.utils;

import java.util.Map;

import com.saji.dashboard_backend.shared.dtos.PaginationFilter;

public class PaginationFilterExtractor implements FilterExtractor<PaginationFilter> {

    @Override
    public PaginationFilter getFilters(Map<String, Object> filters) {
        String pageStr = (String) filters.getOrDefault("page", null);
        Integer page = pageStr != null ? Integer.parseInt(pageStr) : null;
        String sizeStr = (String) filters.getOrDefault("size", null);
        Integer size = sizeStr != null ? Integer.parseInt(sizeStr) : null;
        return new PaginationFilter(page, size);
    }
    
}