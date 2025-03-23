package com.saji.dashboard_backend.modules.location_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.location_management.entites.Country;
import com.saji.dashboard_backend.modules.location_management.services.CountryService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/countries")
public class CountryController extends BaseController<Country>{

    public CountryController(CountryService service) {
        super(service);
    }
    
}
