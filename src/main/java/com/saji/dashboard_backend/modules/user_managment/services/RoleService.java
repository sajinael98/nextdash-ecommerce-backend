package com.saji.dashboard_backend.modules.user_managment.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionDto;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.mappers.RoleMapper;
import com.saji.dashboard_backend.modules.user_managment.repositories.RoleRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class RoleService extends BaseServiceImpl<Role> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RoleService(RoleRepo roleRepo, RoleMapper roleMapper) {
        super(roleRepo);
    }

    public List<PermissionDto> getRolesPermissionsByResource(String resource) {
        final String query = """
                select
                    `resource`,
                    `create_resource` as `create`,
                    `read_resource` as `read`,
                    `update_resource` as `update`,
                    `delete_resource` as `delete`,
                    `role_id` as `roleId`,
                    `role`
                from `res_role_permissions`
                where `resource` = ?
                """;
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PermissionDto.class), resource);
    }

    @Transactional
    public void createRolePermission(PermissionDto request) {
        var role = this.findEntityById(request.getRoleId());

        Permission permission = createOrUpdatePermission(request);
        role.addPermission(permission);
        this.saveEntity(role);
    }

    @Transactional
    public void updateRolePermission(long roleId, PermissionDto request) {
        var role = this.findEntityById(roleId);
        role.getPermissions().forEach(permission -> {
            if (permission.getResource().equalsIgnoreCase(request.getResource())) {
                updatePermission(permission, request);
            }
        });

        this.saveEntity(role);
    }

    public void deleteRolesPermissionsByResource(long roleId, String resource) {
        var role = this.findEntityById(roleId);
        role.removePermission(resource);
        this.saveEntity(role);
    }

    private Permission createOrUpdatePermission(PermissionDto request) {
        Permission permission = new Permission();
        updatePermission(permission, request);
        return permission;
    }

    private void updatePermission(Permission permission, PermissionDto request) {
        permission.setResource(request.getResource());
        permission.setCreate(request.isCreate());
        permission.setRead(request.isRead());
        permission.setUpdate(request.isUpdate());
        permission.setDelete(request.isDelete());
    }

}
