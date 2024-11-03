package com.saji.dashboard_backend.modules.user_managment.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.UserDto;
import com.saji.dashboard_backend.modules.user_managment.dtos.UserRoleDto;
import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.modules.user_managment.entities.User;
import com.saji.dashboard_backend.modules.user_managment.mappers.UserMapper;
import com.saji.dashboard_backend.modules.user_managment.repositories.RoleRepo;
import com.saji.dashboard_backend.modules.user_managment.repositories.UserRepo;
import com.saji.dashboard_backend.shared.services.BaseService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService extends BaseService<User, UserDto> {
    private UserRepo userRepo;
    private RoleRepo roleRepo;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, UserMapper userMapper) {
        super(userRepo, userMapper);
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public List<UserRoleDto> getUserRoles(Long id) {
        User user = findUserById(id);
        return user.getRoles().stream().map(role -> {
            UserRoleDto roleDto = new UserRoleDto();
            roleDto.setId(role.getId());
            roleDto.setRole(role.getRole());
            return roleDto;
        }).toList();
    }

    @Transactional
    public void assignUserRoles(Long id, List<Long> rolesId) {
        User user = findUserById(id);
        List<Role> roles = roleRepo.findAllById(rolesId);

        Set<Long> invalidRolesId = new HashSet<>();
        roles.stream().forEach(role -> {
            if (!rolesId.contains(role.getId())) {
                invalidRolesId.add(role.getId());
            }
        });
        if (invalidRolesId.size() > 0) {
            throw new IllegalArgumentException("Invaled Roles Id: " + invalidRolesId);
        }

        user.setRoles(roles);
        roles.forEach(role -> role.getUsers().add(user));

        userRepo.save(user);
    }

    private User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " is not found."));
    }

    @Override
    public void validate(User object) {
        if (object.getId() == null) {
            if (userRepo.existsByEmail(object.getEmail())) {
                throw new IllegalArgumentException("email: " + object.getEmail() + " is already taken.");
            }
        } else {
            var tempId = userRepo.findIdByEmail(object.getEmail());
            if (tempId != null && tempId != object.getId()) {
                throw new IllegalArgumentException("email: " + object.getEmail() + " is already taken.");
            }
        }
    }

}
