package com.saji.dashboard_backend.shared.services;

import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.specifications.SearchCriteria;
import com.saji.dashboard_backend.shared.specifications.SortCriteria;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends BaseEntity> {
    T create(T entity);

    T update(Long id, T entity);

    T findEntityById(Long id);

    ListResponse<T> getList(List<SearchCriteria> filters, List<SortCriteria> sorts, Pageable defaultPageable);

    List<Map<String, Object>> getList(List<String> fields,
            List<SearchCriteria> filters);

    void deleteEntityById(Long id);

    void confirmResource(Long id);

    void cancelResource(Long id);
}
