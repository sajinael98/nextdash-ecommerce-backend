package com.saji.dashboard_backend.modules.user_managment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.services.UserService;
import com.saji.dashboard_backend.shared.controllers.EnhancedBaseController;

@RestController
@RequestMapping("/users")
public class UserController extends EnhancedBaseController<User> {
    public UserController(UserService service) {
        super(service);
    }
}
