package com.saji.dashboard_backend.secuirty.Interceptors.request_validation;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.saji.dashboard_backend.modules.user_managment.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserValidator implements RequestValidator {
    private RequestValidator validator;

    @Override
    public void setNext(RequestValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        return user.getId() == 1 || validator == null || validator.validate(request, response);
    }

}
