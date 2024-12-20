package com.saji.dashboard_backend.modules.user_managment.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.shared.services.EnhancedBaseService;

@Service
public class UserService extends EnhancedBaseService<User> {

    public UserService(UserRepo userRepo) {
        super(userRepo);
    }
}
