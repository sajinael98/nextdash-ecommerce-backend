package com.saji.dashboard_backend.shared.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.dtos.PaginationFilter;
import com.saji.dashboard_backend.shared.dtos.SorterValue;
import com.saji.dashboard_backend.shared.dtos.ValueFilter;
import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.services.BaseService;
import com.saji.dashboard_backend.shared.utils.FieldFilterExtractor;
import com.saji.dashboard_backend.shared.utils.FieldSorterExtractor;
import com.saji.dashboard_backend.shared.utils.PaginationFilterExtractor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController<Entity extends BaseEntity> {
    private final BaseService<Entity> service;

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
    public ResponseEntity<ListResponse<Entity>> getList(@RequestParam Map<String, Object> params) {
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

        FieldSorterExtractor sorterExtractor = new FieldSorterExtractor();
        Collection<SorterValue> sorters = sorterExtractor.getFilters(params);
        ListResponse<Entity> response = (ListResponse<Entity>) service.getList(paginationFilter, valueFilters,
                sorters);
        // headers.set("Access-Control-Expose-Headers", "X-Total-Count");
        // headers.set("x-total-count", "" + response.getTotal());

        return ResponseEntity.ok().body(response);
    }
}
