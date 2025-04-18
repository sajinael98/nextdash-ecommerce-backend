package com.saji.dashboard_backend.shared.repositories.base;

import java.util.List;
import java.util.Map;

import com.saji.dashboard_backend.shared.specifications.SearchCriteria;
// GenericJpaRepositoryCustom.java
public interface GenericJpaRepositoryCustom<T> {
    List<Map<String, Object>> fetchValues(Class<T> entityClass, List<String> fields, List<SearchCriteria> filters);
}
