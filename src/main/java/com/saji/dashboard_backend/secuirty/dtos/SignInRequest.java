package com.saji.dashboard_backend.secuirty.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String username;
    private String password;
}
