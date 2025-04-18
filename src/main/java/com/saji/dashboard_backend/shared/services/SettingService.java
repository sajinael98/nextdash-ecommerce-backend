package com.saji.dashboard_backend.shared.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.shared.entites.Setting;
import com.saji.dashboard_backend.shared.repositories.SettingRepo;

@Service
public class SettingService extends BaseServiceImpl<Setting>{

    public SettingService(SettingRepo repo) {
        super(repo);
    }
    
}
