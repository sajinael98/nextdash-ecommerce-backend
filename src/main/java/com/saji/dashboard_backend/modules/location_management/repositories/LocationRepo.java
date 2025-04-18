package com.saji.dashboard_backend.modules.location_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.location_management.entites.Location;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface LocationRepo extends GenericJpaRepository<Location, Long>{
    
}
