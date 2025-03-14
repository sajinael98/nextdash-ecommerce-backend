package com.saji.dashboard_backend.modules.user_managment.dtos;

import lombok.Data;

@Data
public class UpdateUserProfile {
    private String firstName;
    private String lastName;
    private String email;
}
