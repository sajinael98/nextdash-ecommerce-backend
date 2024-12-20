package com.saji.dashboard_backend.shared.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.dtos.PaginationFilter;
import com.saji.dashboard_backend.shared.dtos.SorterValue;
import com.saji.dashboard_backend.shared.dtos.ValueFilter;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;
import com.saji.dashboard_backend.shared.specifications.EntitySpecification;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService<Entity extends BaseEntity> {
    private final BaseRepository<Entity, Long> repo;

    public Entity create(Entity entity) {
        return saveEntity(entity);
    }

    public Entity update(Long id, Entity entity) {
        if(!repo.existsById(id)){
            new EntityNotFoundException("resource with id: " + id + " is not found.");
        }
        entity.setId(id);
        return saveEntity(entity);
    }

    public Entity findEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("resource with id: " + id + " is not found."));
    }

    public ListResponse<Entity> getList(PaginationFilter paginationFilter,
            Collection<ValueFilter> valueFilters, Collection<SorterValue> sorters) {
        List<Sort.Order> orders = new ArrayList<>();
        for (SorterValue sorter : sorters) {
            Sort.Direction direction = sorter.getOrder() == com.saji.dashboard_backend.shared.enums.Sort.DESC
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, sorter.getField()));
        }
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(paginationFilter.getPage() - 1,
                paginationFilter.getSize(), sort);

        Page<Entity> entities = repo.findAll(EntitySpecification.findList(valueFilters), pageable);
        
        ListResponse<Entity> response = new ListResponse<>();
        response.setData(entities.getContent());
        response.setTotal(entities.getTotalElements());
        return response;
    }
    public void validate(Entity entity) {

    }

    public Entity saveEntity(Entity entity) {
        validate(entity);
        return repo.save(entity);
    }
}
