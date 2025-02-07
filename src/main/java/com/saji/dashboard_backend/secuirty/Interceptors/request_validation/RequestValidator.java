package com.saji.dashboard_backend.secuirty.Interceptors.request_validation;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestValidator {
    void setNext(RequestValidator validator);

    boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
