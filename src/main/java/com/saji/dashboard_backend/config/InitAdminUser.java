package com.saji.dashboard_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.saji.dashboard_backend.modules.user_managment.entities.AccountInformation;
import com.saji.dashboard_backend.modules.user_managment.entities.PersonalInformation;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitAdminUser implements CommandLineRunner {
    private final UserRepo userRepo;

    private final PasswordEncoder encoder;

    @Value("${app.user.admin.username}")
    private String username;

    @Value("${app.user.admin.password}")
    private String password;

    @Value("${app.user.admin.email}")
    private String email;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.existsById(1L)) {
            return;
        }

        User admin = new User();

        PersonalInformation pInfor = new PersonalInformation();
        pInfor.setFirstName("Saji");
        pInfor.setLastName("Nael");
        admin.setPersonalInformation(pInfor);

        AccountInformation aInfor = new AccountInformation();
        aInfor.setUsername(username);
        aInfor.setPassword(encoder.encode(password));
        aInfor.setEmail(email);
        admin.setAccountInformation(aInfor);

        userRepo.save(admin);
    }
}
