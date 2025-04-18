package com.saji.dashboard_backend.shared.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;
import com.saji.dashboard_backend.shared.specifications.QueryCriteriaBuilder;
import com.saji.dashboard_backend.shared.specifications.SearchCriteria;
import com.saji.dashboard_backend.shared.specifications.SortCriteria;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController<Entity extends BaseEntity> {
    private final BaseServiceImpl<Entity> service;

    @PostMapping
    public ResponseEntity<Entity> create(@Valid @RequestBody Entity entity) {
        entity = service.create(entity);
        return ResponseEntity.ok(entity);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Entity> update(@PathVariable(required = true) Long id, @Valid @RequestBody Entity entity) {
        entity = service.update(id, entity);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entity> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findEntityById(id));
    }

    @GetMapping
    public ResponseEntity<Object> getList(@RequestParam Map<String, String> params,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        List<SearchCriteria> filters = QueryCriteriaBuilder.parseFiltersFromParams(params);
        List<SortCriteria> sorts = QueryCriteriaBuilder.parseSortsFromParams(params);
        
        if (params.containsKey("fields")) {
            return ResponseEntity.ok().body(service.getList(List.of(params.get("fields").split(",")), filters));
        } else {
            return ResponseEntity.ok().body(service.getList(filters, sorts, pageable));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResourceById(@PathVariable(name = "id") Long resourceId) {
        service.deleteEntityById(resourceId);
        return ResponseEntity.noContent().build();
    }
}
