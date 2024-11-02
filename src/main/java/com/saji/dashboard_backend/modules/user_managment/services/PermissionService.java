package com.saji.dashboard_backend.modules.user_managment.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionResponse;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.modules.user_managment.mappers.PermissionMapper;
import com.saji.dashboard_backend.modules.user_managment.repositories.PermissionRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepo permissionRepo;
    private final PermissionMapper permissionMapper;
    public void delete(Long id) {
        if (!permissionRepo.existsById(id)) {
            throw new EntityNotFoundException("Permission with id: " + id + " is not found.");
        }
        permissionRepo.deleteById(id);
    }

    public PermissionResponse update(Long id, PermissionDto req) {
        Permission permission = permissionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission with id: " + id + " is not found."));
        permission.setCreateR(req.isCreateR());
        permission.setReadR(req.isReadR());
        permission.setEditR(req.isEditR());
        permission.setDeleteR(req.isDeleteR());
        
        permission = permissionRepo.save(permission);
        return (PermissionResponse) permissionMapper.convertEntityToResponse(permission);
    }
}
