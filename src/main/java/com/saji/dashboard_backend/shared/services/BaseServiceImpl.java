package com.saji.dashboard_backend.shared.services;

import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.entites.ResourceStatus;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;
import com.saji.dashboard_backend.shared.specifications.GenericSpecification;
import com.saji.dashboard_backend.shared.specifications.QueryCriteriaBuilder;
import com.saji.dashboard_backend.shared.specifications.SearchCriteria;
import com.saji.dashboard_backend.shared.specifications.SortCriteria;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private final GenericJpaRepository<T, Long> repo;

    public BaseServiceImpl(GenericJpaRepository<T, Long> repo) {
        this.repo = repo;
    }

    @Override
    public T create(T entity) {
        return saveEntity(entity);
    }

    @Override
    public T update(Long id, T entity) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("resource with id: " + id + " is not found.");
        }
        entity.setId(id);
        return saveEntity(entity);
    }

    @Override
    public T findEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("resource with id: " + id + " is not found."));
    }

    @Override
    public ListResponse<T> getList(List<SearchCriteria> filters, List<SortCriteria> sorts, Pageable defaultPageable) {
        Sort sort = sorts.isEmpty()
                ? Sort.by(Sort.Order.asc("id"))
                : QueryCriteriaBuilder.buildSort(sorts);

        Specification<T> spec = new GenericSpecification<>(filters);

        Pageable pageable = PageRequest.of(
                defaultPageable.getPageNumber(),
                defaultPageable.getPageSize(),
                sort);

        Page<T> entityPage = repo.findAll(spec, pageable);

        ListResponse<T> response = new ListResponse<>();
        response.setData(entityPage.getContent());
        response.setTotal(entityPage.getTotalElements());

        return response;
    }

    @Override
    public void deleteEntityById(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void confirmResource(Long id) {
        var entity = findEntityById(id);
        if (entity.getStatus() == ResourceStatus.CONFIRMED) {
            throw new RuntimeException("resource with id: " + id + " is already confirmed.");
        }
        entity.setStatus(ResourceStatus.CONFIRMED);
        entity = repo.save(entity);
        afterConfirm(entity);
    }

    @Override
    @Transactional
    public void cancelResource(Long id) {
        var entity = findEntityById(id);
        if (entity.getStatus() == ResourceStatus.DRAFT) {
            throw new RuntimeException("resource with id: " + id + " is already canceled.");
        }
        entity.setStatus(ResourceStatus.DRAFT);
        entity = repo.save(entity);
        afterCancel(entity);
    }

    protected void validate(T entity) {

    }

    protected T saveEntity(T entity) {
        beforeSave(entity);
        validate(entity);
        entity = repo.save(entity);
        afterSave(entity);
        return entity;
    }

    @Transactional
    protected void afterConfirm(T entity) {
    }

    @Transactional
    protected void afterCancel(T entity) {
    }

    @Override
    public ListResponse<Map<String, Object>> getList(List<String> fields, List<SearchCriteria> filters) {
        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
                .getTypeName();
        Class<T> clazz;
        try {
            clazz = (Class<T>) Class.forName(className);
            var values = repo.fetchValues(clazz, fields, filters);
            ListResponse<Map<String, Object>> response = new ListResponse<Map<String, Object>>();
            response.setData(values);
            response.setTotal((long) values.size());
            return response;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void beforeSave(T entity) {
    }

    @Override
    public void afterSave(T entity) {
    }
}
