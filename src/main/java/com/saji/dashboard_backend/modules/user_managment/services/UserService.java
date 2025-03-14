package com.saji.dashboard_backend.modules.user_managment.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.UpdateUserProfile;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.shared.services.BaseService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends BaseService<User> {
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        super(userRepo);
        this.userRepo = userRepo;
    }

    @Transactional
    public void updateProfile(Long userId, UpdateUserProfile profile){
        userRepo.updateUserProfile(profile.getEmail(), profile.getFirstName(), profile.getLastName(), userId);
    }
}
