package com.saji.dashboard_backend.modules.user_managment.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.shared.services.BaseService;

@Service
public class UserService extends BaseService<User> {
    public UserService(UserRepo userRepo) {
        super(userRepo);
    }
}
