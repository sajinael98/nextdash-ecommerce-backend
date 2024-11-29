package com.saji.dashboard_backend.shared.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.saji.dashboard_backend.shared.dtos.BaseDto;
import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.dtos.PaginationFilter;
import com.saji.dashboard_backend.shared.dtos.SorterValue;
import com.saji.dashboard_backend.shared.dtos.ValueFilter;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;
import com.saji.dashboard_backend.shared.specifications.EntitySpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService<Entity extends BaseEntity, EntityDto extends BaseDto> {
    private final BaseRepository<Entity, Long> baseRepository;
    private final BaseMapper<Entity, EntityDto> baseMapper;

    @Transactional
    public BaseDto create(EntityDto request) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Entity object = baseMapper.convertRequestToEntity(request);
        validate(object);
        object = baseRepository.save(object);
        Entity savedObject = baseRepository.findById(object.getId()).get();
        return baseMapper.convertEntityToResponse(savedObject);
    }

    @Transactional
    public BaseDto update(Long id, EntityDto request) {
        if (!baseRepository.existsById(id)) {
            new EntityNotFoundException("resoure is not found.");
        }
        Entity object = baseMapper.convertRequestToEntity(request);
        object.setId(id);
        validate(object);
        object = baseRepository.save(object);
        return baseMapper.convertEntityToResponse(object);
    }

    public ListResponse<BaseDto> getList(PaginationFilter paginationFilter,
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

        Page<Entity> entities = baseRepository.findAll(EntitySpecification.findList(valueFilters), pageable);
        List<BaseDto> list = (List<BaseDto>) entities.stream()
                .map(entity -> baseMapper.convertEntityToResponse(entity))
                .toList();
        ListResponse<BaseDto> response = new ListResponse<>();
        response.setData(list);
        response.setTotal(entities.getTotalElements());
        return response;
    }

    public BaseDto getById(Long id) {
        var entity = baseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        BaseDto response = baseMapper.convertEntityToResponse(entity);
        return response;
    }

    @Transactional
    public void deleteById(Long id) {
        if (!baseRepository.existsById(id)) {
            new EntityNotFoundException("resoure is not found.");
        }
        baseRepository.deleteById(id);
    }

    public void validate(Entity object) {

    }
}
