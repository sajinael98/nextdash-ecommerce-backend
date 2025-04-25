package com.saji.dashboard_backend.shared.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.saji.dashboard_backend.shared.entites.BaseResource;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

public abstract class ConfirmableController<Entity extends BaseResource> extends BaseController<Entity> {
    private BaseServiceImpl<Entity> service;

    public ConfirmableController(BaseServiceImpl<Entity> service) {
        super(service);
        this.service = service;
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmResource(@PathVariable(required = true) Long id) {
        service.confirmResource(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelResource(@PathVariable(required = true) Long id) {
        service.cancelResource(id);
        return ResponseEntity.noContent().build();
    }
}
