package com.saji.dashboard_backend.modules.user_managment.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.dtos.UserDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.UserRoleDto;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.services.UserService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserDto, UserDto> {
    private UserService userService;

    public UserController(UserService service) {
        super(service);
        this.userService = service;
    }

    @GetMapping("/{user-id}/roles")
    public ResponseEntity<List<UserRoleDto>> getUserRoles(@PathVariable(name = "user-id") Long id) {
        return ResponseEntity.ok().body(userService.getUserRoles(id));
    }

    @PostMapping("/{user-id}/roles")
    public ResponseEntity<Void> assign(@PathVariable(name = "user-id") Long id, @RequestBody List<Long> rolesId) {
        if (rolesId == null || rolesId.isEmpty()) {
            throw new IllegalArgumentException("rolesId is required.");
        }
        userService.assignUserRoles(id, rolesId);
        return ResponseEntity.ok().build();
    }
}
