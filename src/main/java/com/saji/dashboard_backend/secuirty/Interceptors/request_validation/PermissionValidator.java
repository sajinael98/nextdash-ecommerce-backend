package com.saji.dashboard_backend.secuirty.Interceptors.request_validation;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.entities.UserRole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PermissionValidator implements RequestValidator {
    @PersistenceContext
    private EntityManager entityManager;

    private RequestValidator validator;

    @Override
    public void setNext(RequestValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String entity = request.getHeader("x-entity");
        String action = request.getHeader("x-action");

        String columnName = action + "_r";

        String sql = String.format("""
                select
                    roleId
                from
                    res_role_permissions
                where
                    %s = 1
                    and resource = :resource
                """, columnName);

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("resource", entity);

        List<Long> results = query.getResultList();
       
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        boolean hasPermission = user.getRoles().stream()
                .map(UserRole::getRoleId)
                .anyMatch(results::contains);

        if (!hasPermission) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("You don't have permission.");
            return false;
        }

        return validator == null || validator.validate(request, response);
    }

}
