package com.saji.dashboard_backend.modules.stock_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.stock_management.entities.Variant;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;

@Repository
public interface VariantRepo extends BaseRepository<Variant, Long> {
    
}
