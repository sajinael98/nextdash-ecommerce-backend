package com.saji.dashboard_backend.secuirty.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.saji.dashboard_backend.secuirty.filters.JwtAuthenticationFilter;
import com.saji.dashboard_backend.secuirty.services.LogoutService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigration {
        // no need to add /api/**
        private final static String[] WHITELIST_URL = {
                        "/sys-auth/**",
                        "/files/**"
        };

        private final JwtAuthenticationFilter jwtAuthFilter;

        private final LogoutService logoutHandler;

        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(req -> req.requestMatchers(WHITELIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated());

                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http.authenticationProvider(authenticationProvider);

                http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
               
                http.cors(c -> c.configurationSource(corsConfigurationSource()));
               
                http.csrf(AbstractHttpConfigurer::disable);

                http.logout(logout -> logout.logoutUrl("/api/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                                (request, response,
                                                                authentication) -> SecurityContextHolder
                                                                                .clearContext()));
                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOrigin("http://localhost:3000");
                corsConfiguration.setAllowedMethods(Arrays.asList(
                                HttpMethod.GET.name(),
                                HttpMethod.HEAD.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.PATCH.name(),
                                HttpMethod.DELETE.name()));
                corsConfiguration.setMaxAge(1800L);
                source.registerCorsConfiguration("/**", corsConfiguration); // you restrict your path here
                return source;
        }
}
