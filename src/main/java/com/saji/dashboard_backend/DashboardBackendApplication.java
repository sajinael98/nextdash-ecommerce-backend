package com.saji.dashboard_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.secuirty.dtos.SignInRequest;
import com.saji.dashboard_backend.secuirty.services.AuthService;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DashboardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(PasswordEncoder encoder, UserRepo repo, AuthService uAuthService) {
		return args -> {
			User user = repo.findById(1L).get();
			user.setPassword(encoder.encode("123456"));
			repo.save(user);
			SignInRequest request = new SignInRequest();
			request.setUsername("system-admin");
			request.setPassword("123456");
			// System.out.println(uAuthService.signIn(request).getToken());
		};
	}
}
