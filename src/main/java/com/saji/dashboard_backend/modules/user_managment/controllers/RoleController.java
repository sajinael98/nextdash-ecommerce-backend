package com.saji.dashboard_backend.modules.user_managment.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.services.RoleService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role> {
    private RoleService roleService;

    public RoleController(RoleService rolesService) {
        super(rolesService);
        this.roleService = rolesService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<Void> createRolePermission(
            @RequestBody PermissionDto request) {
        roleService.createRolePermission(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{role-id}/permissions")
    public ResponseEntity<Void> updateRolePermission(
            @PathVariable(name = "role-id") long roleId,
            @RequestBody PermissionDto request) {
        System.out.println(request.isCreate());
        roleService.updateRolePermission(roleId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDto>> getRolesPermissionsByResource(
            @RequestParam(required = true) String resource) {
        return ResponseEntity.ok().body(roleService.getRolesPermissionsByResource(resource));
    }

    @DeleteMapping("/{role-id}/permissions")
    public ResponseEntity<Void> deleteRolesPermissionsByResource(
            @PathVariable(name = "role-id") long roleId,
            @RequestParam(required = true) String resource) {
        roleService.deleteRolesPermissionsByResource(roleId, resource);
        return ResponseEntity.noContent().build();
    }
}
