package com.saji.dashboard_backend.modules.user_managment.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotEmpty
    @Size(min = 6)
    private String password;
}
