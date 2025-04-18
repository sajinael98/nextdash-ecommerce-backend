package com.saji.dashboard_backend.shared.controllers;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.saji.dashboard_backend.shared.entites.BaseEntity;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

public abstract class ConfirmableController<Entity extends BaseEntity> extends BaseController<Entity> {
    private BaseServiceImpl<Entity> service;

    public ConfirmableController(BaseServiceImpl<Entity> service) {
        super(service);
        this.service = service;
    }

    @PatchMapping("/{id}/confirm")
    public void confirmResource(@PathVariable(required = true) Long id) {
        service.confirmResource(id);
    }

    @PatchMapping("/{id}/cancel")
    public void cancelResource(@PathVariable(required = true) Long id) {
        service.cancelResource(id);
    }
}
