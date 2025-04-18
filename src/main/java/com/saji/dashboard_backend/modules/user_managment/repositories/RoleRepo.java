package com.saji.dashboard_backend.modules.user_managment.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.user_managment.entities.Role;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface RoleRepo extends GenericJpaRepository<Role, Long>{
    List<Role> findByRoleIn(List<String> roles);   
}
