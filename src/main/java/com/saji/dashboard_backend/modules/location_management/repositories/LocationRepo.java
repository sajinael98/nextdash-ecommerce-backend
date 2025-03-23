package com.saji.dashboard_backend.modules.location_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.location_management.entites.Location;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;

@Repository
public interface LocationRepo extends BaseRepository<Location, Long>{
    
}
