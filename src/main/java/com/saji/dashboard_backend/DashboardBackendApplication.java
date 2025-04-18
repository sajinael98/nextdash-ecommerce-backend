package com.saji.dashboard_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepositoryCustomImpl;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.saji.dashboard_backend", repositoryBaseClass = GenericJpaRepositoryCustomImpl.class)
public class DashboardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardBackendApplication.class, args);
	}
}
