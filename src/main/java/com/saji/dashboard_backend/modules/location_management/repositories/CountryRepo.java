package com.saji.dashboard_backend.modules.location_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.location_management.entites.Country;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;

@Repository
public interface CountryRepo extends BaseRepository<Country, Long> {

}
