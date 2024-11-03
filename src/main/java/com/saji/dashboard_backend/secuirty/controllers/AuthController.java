package com.saji.dashboard_backend.secuirty.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.dtos.ChangePasswordRequest;
import com.saji.dashboard_backend.secuirty.dtos.SignInRequest;
import com.saji.dashboard_backend.secuirty.dtos.SignInResponse;
import com.saji.dashboard_backend.secuirty.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sys-auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest req) {
                var response = authService.signIn(req);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{user-id}/password")
    // @PreAuthorize("hasRole('ROLE_SYSTEM_MANAGER')") 
    public ResponseEntity<Void> changePassword(@PathVariable(name = "user-id") Long id,
            @Valid @RequestBody ChangePasswordRequest req) {
        authService.changePassword(id, req);
        return ResponseEntity.ok().build();
    }
}
