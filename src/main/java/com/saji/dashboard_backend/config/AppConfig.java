package com.saji.dashboard_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.secuirty.Interceptors.request_validation.PermissionValidator;
import com.saji.dashboard_backend.secuirty.Interceptors.request_validation.RequestHeaderValidator;
import com.saji.dashboard_backend.secuirty.Interceptors.request_validation.RequestValidator;
import com.saji.dashboard_backend.secuirty.Interceptors.request_validation.UserValidator;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepo userRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new ApplicationAuditingAware();
    }

    @Bean
    public RequestValidator requestValidator(RequestHeaderValidator requestHeaderValidator,
            UserValidator userValidator, PermissionValidator permissionValidator) {
        userValidator.setNext(requestHeaderValidator);
        requestHeaderValidator.setNext(permissionValidator);

        return userValidator;
    }
}
