package com.saji.dashboard_backend.modules.stock_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.stock_management.entities.Variant;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface VariantRepo extends GenericJpaRepository<Variant, Long> {
    
}
