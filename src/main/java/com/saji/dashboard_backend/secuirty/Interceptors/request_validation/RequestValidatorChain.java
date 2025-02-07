package com.saji.dashboard_backend.secuirty.Interceptors.request_validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestValidatorChain implements HandlerInterceptor {

    @Autowired
    private RequestValidator requestValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return requestValidator.validate(request, response);
    }
}
