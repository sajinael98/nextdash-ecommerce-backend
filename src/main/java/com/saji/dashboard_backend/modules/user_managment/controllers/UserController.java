package com.saji.dashboard_backend.modules.user_managment.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.dtos.UpdateUserProfile;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.services.UserService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User> {
    private UserService service;

    public UserController(UserService service) {
        super(service);
        this.service = service;
    }

    @PatchMapping("/{id}/update-profile")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Void> updateUserProfile(@PathVariable(required = true, name = "id") long userId,
            @RequestBody UpdateUserProfile profile) {
        service.updateProfile(userId, profile);
        return ResponseEntity.ok().build();
    }
}
