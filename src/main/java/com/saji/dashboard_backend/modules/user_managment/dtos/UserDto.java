package com.saji.dashboard_backend.modules.user_managment.dtos;

import com.saji.dashboard_backend.shared.dtos.BaseRequest;
import com.saji.dashboard_backend.shared.dtos.BaseResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto implements BaseRequest, BaseResponse {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
}
