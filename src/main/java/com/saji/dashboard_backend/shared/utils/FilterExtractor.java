package com.saji.dashboard_backend.shared.utils;

import java.util.Map;

public interface FilterExtractor<T> {
    public T getFilters(Map<String, Object> filters);
}