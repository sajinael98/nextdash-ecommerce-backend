package com.saji.dashboard_backend.shared.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.shared.entites.Setting;
import com.saji.dashboard_backend.shared.services.SettingService;

@RestController
@RequestMapping("/settings")
public class SettingController extends BaseController<Setting> {

    public SettingController(SettingService service) {
        super(service);
    }
    
}
