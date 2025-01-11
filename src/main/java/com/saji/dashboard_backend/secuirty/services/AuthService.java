package com.saji.dashboard_backend.secuirty.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.ChangePasswordRequest;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.entities.Token;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.RoleRepo;
import com.saji.dashboard_backend.modules.user_managment.repositories.TokenRepo;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.secuirty.dtos.SignInRequest;
import com.saji.dashboard_backend.secuirty.dtos.SignInResponse;
import com.saji.dashboard_backend.secuirty.utils.PermissionUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final TokenRepo tokenRepo;

    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final PasswordEncoder encoder;

    private final RoleRepo roleRepo;

    public SignInResponse signIn(SignInRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);
        SignInResponse response = new SignInResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setToken(token);
        response.setId(user.getId());
        List<String> roles = user.getRoles().stream().map(role -> role.getRole()).toList();
        System.out.println(roles);
        response.setRoles(new HashSet<>(roles));
        List<Role> rolesList = roleRepo.findByRoleIn(roles);
        System.out.println(rolesList);
        Set<Permission> permissions = rolesList.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());
        response.setPermissions(permissions);
        revokeAllUserTokens(user);
        saveUserToken(user, token);

        return response;
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest req) {
        String encodedPassword = encoder.encode(req.getPassword());
        User user = userRepo.findById(id).get();
        user.setPassword(encodedPassword);

        userRepo.save(user);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);

        tokenRepo.save(token);
    }
}
