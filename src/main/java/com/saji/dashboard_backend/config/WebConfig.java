package com.saji.dashboard_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.saji.dashboard_backend.secuirty.Interceptors.request_validation.RequestValidatorChain;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RequestValidatorChain requestValidatorChain;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestValidatorChain)
                .addPathPatterns("/**") // Apply to all endpoints
                .excludePathPatterns(new String[] { "/sys-auth/**", "/files/**" }); // Exclude whitelisted URLs
    }
}