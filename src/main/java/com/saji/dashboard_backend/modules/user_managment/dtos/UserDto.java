package com.saji.dashboard_backend.modules.user_managment.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.saji.dashboard_backend.shared.dtos.BaseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto extends BaseDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    private List<UserRoleDto> roles = Collections.EMPTY_LIST;
}
