package com.saji.dashboard_backend.modules.location_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.location_management.entites.Country;
import com.saji.dashboard_backend.modules.location_management.repositories.CountryRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

@Service
public class CountryService extends BaseServiceImpl<Country> {

    public CountryService(CountryRepo repo) {
        super(repo);
    }

}
