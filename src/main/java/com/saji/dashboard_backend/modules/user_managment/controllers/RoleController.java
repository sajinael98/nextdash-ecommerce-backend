package com.saji.dashboard_backend.modules.user_managment.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionResponse;
import com.saji.dashboard_backend.modules.user_managment.dtos.RoleDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.RoleResponse;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.services.RoleService;
import com.saji.dashboard_backend.shared.controllers.BaseController;
import com.saji.dashboard_backend.shared.dtos.ListResponse;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleDto, RoleResponse> {
    private RoleService roleService;

    public RoleController(RoleService rolesService) {
        super(rolesService);
        this.roleService = rolesService;
    }

    @GetMapping("/{role-id}/permissions")
    public ResponseEntity<ListResponse<PermissionResponse>> getPermissionsByRoleId(@PathVariable(name = "role-id") Long id) {
        return ResponseEntity.ok().body(roleService.getPermissions(id));
    }
    
    @PostMapping("/{role-id}/permissions")
    public ResponseEntity<ListResponse<PermissionResponse>> createPermission(@PathVariable(name = "role-id") Long id,
    @RequestBody PermissionDto request) {
        System.out.println(request);
        return ResponseEntity.ok().body(roleService.createPermission(id, request));
    }
}
