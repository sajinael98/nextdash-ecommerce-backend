package com.saji.dashboard_backend.modules.user_managment.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saji.dashboard_backend.secuirty.utils.PermissionUtils;
import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")

public class Role extends BaseEntity {
    @Column(nullable = false, length = 25)
    private String role;

    @Column(columnDefinition = "INT")
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = {
            @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false),
            @JoinColumn(name = "role", referencedColumnName = "role", nullable = false) }, uniqueConstraints = @UniqueConstraint(columnNames = {
                    "role" }))
    private Set<Permission> permissions = new HashSet<>();
    
    @JsonIgnore
    public List<SimpleGrantedAuthority> getgrantedAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission p : permissions) {
            if (p.isCreate()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(PermissionUtils.customizePermission(p.getResource(),
                        "create")));
            }
            if (p.isRead()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(PermissionUtils.customizePermission(p.getResource(),
                        "read")));
            }
            if (p.isUpdate()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(PermissionUtils.customizePermission(p.getResource(),
                        "update")));
            }
            if (p.isDelete()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(PermissionUtils.customizePermission(p.getResource(),
                        "delete")));
            }
        }
        return grantedAuthorities;
    }

    public boolean addPermission(Permission permission) {
        if (permissions.contains(permission)) {
            throw new IllegalArgumentException("resouce: " + permission.getResource() + " has already a permission.");
        }
        if (!permission.isValid()) {
            throw new IllegalArgumentException(
                    "At least one of the permissions (create, read, update, delete) must be set to true for the resource "
                            + permission.getResource());
        }
        return this.permissions.add(permission);
    }

    public boolean removePermission(String resouce) {
        return this.permissions.removeIf(permission -> permission.getResource().equals(resouce));
    }
}
