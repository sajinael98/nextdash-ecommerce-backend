package com.saji.dashboard_backend.shared.services;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.saji.dashboard_backend.shared.dtos.BaseRequest;
import com.saji.dashboard_backend.shared.dtos.BaseResponse;
import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.dtos.PaginationFilter;
import com.saji.dashboard_backend.shared.dtos.ValueFilter;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;
import com.saji.dashboard_backend.shared.specifications.EntitySpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseService<Entity extends BaseEntity, Request extends BaseRequest, Response extends BaseResponse> {
    private final BaseRepository<Entity, Long> baseRepository;
    private final BaseMapper<Entity> baseMapper;
    private final Class<Entity> clazz;

    @Transactional
    public Response create(Request request) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Entity object = baseMapper.convertRequestToEntity(clazz.getDeclaredConstructor().newInstance(), request);
        validate(object);
        object = baseRepository.save(object);
        Entity savedObject = baseRepository.findById(object.getId()).get();
        return (Response) baseMapper.convertEntityToResponse(savedObject);
    }

    @Transactional
    public Response update(Long id, Request request) {
        var requiredEntity = baseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("resoure is not found."));
        Entity object = baseMapper.convertRequestToEntity(requiredEntity, request);
        object.setId(id);
        validate(object);
        object = baseRepository.save(object);
        return (Response) baseMapper.convertEntityToResponse(object);
    }

    public ListResponse<Response> getList(PaginationFilter paginationFilter,
            Collection<ValueFilter> valueFilters) {
        Pageable pageable = PageRequest.of(paginationFilter.getPage() - 1,
                paginationFilter.getSize());
        Page<Entity> entities = baseRepository.findAll(EntitySpecification.findList(valueFilters), pageable);
        List<Response> list = (List<Response>) entities.stream()
                .map(entity -> baseMapper.convertEntityToResponse(entity))
                .toList();
        ListResponse<Response> response = new ListResponse<>();
        response.setData(list);
        response.setTotal(entities.getTotalElements());
        return response;
    }

    public Response getById(Long id) {
        var entity = baseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        Response response = (Response) baseMapper.convertEntityToResponse(entity);
        return response;
    }

    public void deleteById(Long id) {
        if (!baseRepository.existsById(id)) {

        }
        baseRepository.deleteById(id);
    }

    public void validate(Entity object) {

    }
}
