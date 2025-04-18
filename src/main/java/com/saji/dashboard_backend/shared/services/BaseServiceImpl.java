package com.saji.dashboard_backend.shared.services;

import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
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
        afterConfirm(entity);
    }

    @Override
    @Transactional
    public void cancelResource(Long id) {
        var entity = findEntityById(id);
        afterCancel(entity);
    }

    protected void validate(T entity) {

    }

    protected T saveEntity(T entity) {
        validate(entity);
        return repo.save(entity);
    }

    @Transactional
    protected void afterConfirm(T entity) {
    }

    @Transactional
    protected void afterCancel(T entity) {
    }

    @Override
    public List<Map<String, Object>> getList(List<String> fields, List<SearchCriteria> filters) {
        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
                .getTypeName();
        Class<T> clazz;
        try {
            clazz = (Class<T>) Class.forName(className);
            return repo.fetchValues(clazz, fields, filters);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
