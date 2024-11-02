package com.saji.dashboard_backend.secuirty.Interceptors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.secuirty.services.JwtService;
import com.saji.dashboard_backend.secuirty.utils.PermissionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private final String[] WHITELIST_URL;

    private final List<String> ACTIONS;

    private final Map<String, String> allowedMethods;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;

    public PermissionInterceptor() {
        WHITELIST_URL = new String[] { "/sys-auth", "/files" };

        ACTIONS = List.of("create", "read", "update", "delete");

        allowedMethods = new HashMap<>();
        allowedMethods.put(HttpMethod.POST.name(), "create");
        allowedMethods.put(HttpMethod.GET.name(), "read");
        allowedMethods.put(HttpMethod.PUT.name(), "update");
        allowedMethods.put(HttpMethod.DELETE.name(), "delete");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        if (Arrays.stream(WHITELIST_URL).anyMatch(uri -> requestURI.startsWith(uri))) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("user is not found."));

        if (!jwtService.isTokenValid(token, user)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        if (user.getId() == 1) {
            return true;
        }

        String method = request.getMethod();
        String entity = request.getHeader("x-entity");
        String action = request.getHeader("x-action");
        if (entity == null || entity.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "x-entity header is required.");
            return false;
        }
        if (action == null || !ACTIONS.contains(action) || !allowedMethods.getOrDefault(method, "").equals(action)) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "x-action header is required.");
            return false;
        }

        if (!PermissionUtils.hasPermission(user, entity, action)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
