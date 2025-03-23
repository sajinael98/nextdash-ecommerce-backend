package com.saji.dashboard_backend.modules.location_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.location_management.entites.Location;
import com.saji.dashboard_backend.modules.location_management.services.LocationService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/locations")
public class LocationController extends BaseController<Location> {

    public LocationController(LocationService service) {
        super(service);
    }
    
}
