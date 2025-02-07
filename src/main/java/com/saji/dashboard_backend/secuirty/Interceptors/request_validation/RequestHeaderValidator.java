package com.saji.dashboard_backend.secuirty.Interceptors.request_validation;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestHeaderValidator implements RequestValidator {
    private RequestValidator validator;

    @Override
    public void setNext(RequestValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        String entity = request.getHeader("x-entity");
        String action = request.getHeader("x-action");

        if (isInvalidEntity(entity)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 403 Forbidden
            response.getWriter().write("x-entity header is required.");
            return false;
        }

        if (isInvalidAction(action, method)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 403 Forbidden
            response.getWriter().write("x-action header is required.");
            return false;
        }

        return validator == null || validator.validate(request, response);
    }

    private boolean isInvalidEntity(String entity) {
        return entity == null || entity.isEmpty();
    }

    private boolean isInvalidAction(String action, String method) {
        switch (action) {
            case "create": {
                return method == HttpMethod.POST.name();
            }

            case "read": {
                return method == HttpMethod.GET.name();
            }

            case "update": {
                return method == HttpMethod.PATCH.name() || method == HttpMethod.PUT.name();
            }

            case "delete": {
                return method == HttpMethod.DELETE.name();
            }

            default:
                return true;
        }
    }
}
