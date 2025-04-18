package com.saji.dashboard_backend.modules.location_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.location_management.entites.Location;
import com.saji.dashboard_backend.modules.location_management.repositories.LocationRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

@Service
public class LocationService extends BaseServiceImpl<Location> {

    public LocationService(LocationRepo repo) {
        super(repo);
    }

}
