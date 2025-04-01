package com.saji.dashboard_backend.modules.stock_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Variant;
import com.saji.dashboard_backend.modules.stock_management.repositories.VariantRepo;
import com.saji.dashboard_backend.shared.services.BaseService;

@Service
public class VariantService extends BaseService<Variant>{

    public VariantService(VariantRepo repo) {
        super(repo);
    }
    
}
