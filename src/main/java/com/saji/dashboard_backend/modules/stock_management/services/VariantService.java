package com.saji.dashboard_backend.modules.stock_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Variant;
import com.saji.dashboard_backend.modules.stock_management.repositories.VariantRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

@Service
public class VariantService extends BaseServiceImpl<Variant> {

    public VariantService(VariantRepo repo) {
        super(repo);
    }

}
