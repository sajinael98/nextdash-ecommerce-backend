package com.saji.dashboard_backend.modules.user_managment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.UpdateUserProfile;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

import org.springframework.transaction.annotation.Transactional;

@Service

public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Transactional
    public void updateProfile(Long userId, UpdateUserProfile profile) {
        userRepo.updateUserProfile(profile.getEmail(), profile.getFirstName(), profile.getLastName(), userId);
    }
}
