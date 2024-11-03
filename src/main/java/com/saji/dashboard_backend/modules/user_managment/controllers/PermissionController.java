package com.saji.dashboard_backend.modules.user_managment.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.services.PermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService service;

    @DeleteMapping("/{permission-id}")
    public ResponseEntity<Void> deletePermission(@PathVariable(name = "permission-id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{permission-id}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable(name = "permission-id") Long id,
            @RequestBody PermissionDto req) {
        return ResponseEntity.ok().body(service.update(id, req));
    }
}
