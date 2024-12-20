package com.saji.dashboard_backend.modules.user_managment.mappers;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.UserDto;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;

@Service
public class UserMapper implements BaseMapper<User, UserDto> {
    @Override
    public User createEntity() {
        return new User();
    }

    @Override
    public UserDto createEntityDto() {
        return new UserDto();        
    }
    
}
