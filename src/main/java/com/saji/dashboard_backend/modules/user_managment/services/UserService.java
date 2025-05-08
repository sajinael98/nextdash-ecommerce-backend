package com.saji.dashboard_backend.modules.user_managment.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saji.dashboard_backend.modules.user_managment.dtos.UpdateUserProfile;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

@Service
public class UserService extends BaseServiceImpl<User> {
    private UserRepo userRepo;

    public UserService(UserRepo repo) {
        super(repo);
        this.userRepo = repo;
    }

    @Transactional
    public void updateProfile(Long userId, UpdateUserProfile profile) {
        userRepo.updateUserProfile(profile.getEmail(), profile.getFirstName(), profile.getLastName(), userId);
    }
}
