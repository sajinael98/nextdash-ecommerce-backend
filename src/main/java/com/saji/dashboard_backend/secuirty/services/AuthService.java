package com.saji.dashboard_backend.secuirty.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.ChangePasswordRequest;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.modules.user_managment.entities.PersonalInformation;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.entities.Token;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.entities.UserRole;
import com.saji.dashboard_backend.modules.user_managment.repositories.RoleRepo;
import com.saji.dashboard_backend.modules.user_managment.repositories.TokenRepo;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.secuirty.dtos.SignInRequest;
import com.saji.dashboard_backend.secuirty.dtos.SignInResponse;

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
        // Authenticate the user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = (User) auth.getPrincipal();
        String token = jwtService.generateToken(user);

        // Build the response
        SignInResponse response = createSignInResponse(user, token);

        // Revoke existing tokens and save the new token
        revokeAllUserTokens(user);
        saveUserToken(user, token);

        return response;
    }

    private SignInResponse createSignInResponse(User user, String token) {
        SignInResponse response = new SignInResponse();
        response.setEmail(user.getAccountInformation().getEmail());
        response.setUsername(user.getUsername());
        response.setToken(token);
        response.setId(user.getId());
        
        PersonalInformation personalInformation = user.getPersonalInformation();
        response.setProfileImage(personalInformation.getProfileImage());
        response.setFullName(
                personalInformation.getFirstName() + " " + personalInformation.getLastName());

        if (user.getId() != 1) {
            response.setRoles(getUserRoles(user));
            response.setPermissions(getUserPermissions(user));
        }

        return response;
    }

    private Set<String> getUserRoles(User user) {
        return roleRepo.findAllById(
                user.getRoles().stream()
                        .map(UserRole::getRoleId)
                        .collect(Collectors.toList()))
                .stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());
    }

    private Set<Permission> getUserPermissions(User user) {
        List<Role> roles = roleRepo
                .findAllById(user.getRoles().stream().map(userRole -> userRole.getRoleId()).toList());

        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest req) {
        String encodedPassword = encoder.encode(req.getPassword());
        User user = userRepo.findById(id).get();
        user.getAccountInformation().setPassword(encodedPassword);

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
