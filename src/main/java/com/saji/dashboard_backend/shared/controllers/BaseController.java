package com.saji.dashboard_backend.shared.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.saji.dashboard_backend.shared.dtos.BaseDto;
import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.dtos.PaginationFilter;
import com.saji.dashboard_backend.shared.dtos.ValueFilter;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.services.BaseService;
import com.saji.dashboard_backend.shared.utils.FieldFilterExtractor;
import com.saji.dashboard_backend.shared.utils.PaginationFilterExtractor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseController<Entity extends BaseEntity, EntityDto extends BaseDto> {
    private final BaseService<Entity, EntityDto> service;

    @PostMapping
    public ResponseEntity<EntityDto> create(@Valid @RequestBody EntityDto request) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return (ResponseEntity<EntityDto>) ResponseEntity.ok().body(service.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityDto> update(@PathVariable Long id,
            @Valid @RequestBody EntityDto request) {
        return (ResponseEntity<EntityDto>) ResponseEntity.ok().body(service.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityDto> getById(@PathVariable Long id) {
        return (ResponseEntity<EntityDto>) ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<ListResponse<EntityDto>> getList(@RequestParam Map<String, Object> params) {
        PaginationFilterExtractor paginationFilterExtractor = new PaginationFilterExtractor();
        PaginationFilter paginationFilter = paginationFilterExtractor.getFilters(params);
        if (paginationFilter.getPage() == null) {
            throw new IllegalArgumentException("page is required.");
        } else if (paginationFilter.getPage() < 1) {
            throw new IllegalArgumentException("page should not be less than 1.");
        }

        if (paginationFilter.getSize() == null) {
            throw new IllegalArgumentException("size is required.");
        } else if (paginationFilter.getSize() < 1) {
            throw new IllegalArgumentException("size should not be less than 1.");
        }

        FieldFilterExtractor fieldFilterExtractor = new FieldFilterExtractor();
        Collection<ValueFilter> valueFilters = fieldFilterExtractor.getFilters(params);
        ListResponse<EntityDto> response = (ListResponse<EntityDto>) service.getList(paginationFilter, valueFilters);

        // headers.set("Access-Control-Expose-Headers", "X-Total-Count");
        // headers.set("x-total-count", "" + response.getTotal());

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}