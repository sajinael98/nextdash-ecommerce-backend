package com.saji.dashboard_backend.secuirty.dtos;

import java.util.Set;

import com.saji.dashboard_backend.modules.user_managment.entities.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private Set<String> roles;
    private Set<Permission> permissions;
}
