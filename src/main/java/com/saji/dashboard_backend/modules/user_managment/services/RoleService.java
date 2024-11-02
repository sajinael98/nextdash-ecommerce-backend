package com.saji.dashboard_backend.modules.user_managment.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionResponse;
import com.saji.dashboard_backend.modules.user_managment.dtos.RoleDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.RoleResponse;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.mappers.PermissionMapper;
import com.saji.dashboard_backend.modules.user_managment.mappers.RoleMapper;
import com.saji.dashboard_backend.modules.user_managment.repositories.PermissionRepo;
import com.saji.dashboard_backend.modules.user_managment.repositories.RoleRepo;
import com.saji.dashboard_backend.shared.dtos.ListResponse;
import com.saji.dashboard_backend.shared.services.BaseService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService extends BaseService<Role, RoleDto, RoleResponse> {
    private RoleRepo roleRepo;
    private PermissionMapper permissionMapper;
    private PermissionRepo permissionRepo;

    public RoleService(RoleRepo roleRepo, RoleMapper roleMapper, PermissionMapper permissionMapper,
            PermissionRepo permissionRepo) {
        super(roleRepo, roleMapper, Role.class);
        this.roleRepo = roleRepo;
        this.permissionMapper = permissionMapper;
        this.permissionRepo = permissionRepo;
    }

    public ListResponse<PermissionResponse> createPermission(Long roleId, PermissionDto permissionDto) {
        Role role = getRoleById(roleId);
        Permission permission = permissionMapper.convertRequestToEntity(new Permission(), permissionDto);
        permission.setRole(role);
        List<Permission> permissions = role.getPermissions();
        permissions.add(permission);
        role.setPermissions(permissions);
        validate(role);
        roleRepo.save(role);
        return prepaListResponse(role);
    }

    public ListResponse<PermissionResponse> deletePermission(Long roleId, Long permissionId) {
        Role role = getRoleById(roleId);
        role.getPermissions().removeIf(p -> p.getId() == permissionId);
        permissionRepo.deleteById(permissionId);
        roleRepo.save(role);
        return prepaListResponse(role);
    }

    public ListResponse<PermissionResponse> getPermissions(Long roleId) {
        Role role = getRoleById(roleId);
        return prepaListResponse(role);
    }

    private Role getRoleById(Long roleId) {
        return roleRepo.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Resource: roles with id " + roleId + " is not found."));
    }

    private List<PermissionResponse> getPermissionResponseList(Role role) {
        return role.getPermissions().stream()
                .map(p -> (PermissionResponse) permissionMapper.convertEntityToResponse(p)).toList();
    }

    private ListResponse<PermissionResponse> prepaListResponse(Role role) {
        List<PermissionResponse> permissionResponses = getPermissionResponseList(role);
        ListResponse<PermissionResponse> response = new ListResponse<>();
        response.setData(permissionResponses);
        response.setTotal((long) permissionResponses.size());
        return response;
    }

    @Override
    public void validate(Role object) {
        List<String> duplicatedPermissions = object.getPermissions().stream()
                .collect(Collectors.groupingBy(Permission::getEntity)).entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1).map(entry -> entry.getKey()).toList();
        if (duplicatedPermissions.size() > 0) {
            throw new IllegalArgumentException("There are a duplicated permissions" + duplicatedPermissions);
        }
    }

}
