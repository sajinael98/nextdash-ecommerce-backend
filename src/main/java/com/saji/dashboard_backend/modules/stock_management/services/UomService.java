package com.saji.dashboard_backend.modules.stock_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Uom;
import com.saji.dashboard_backend.modules.stock_management.repositories.UomRepo;
import com.saji.dashboard_backend.shared.services.BaseService;

@Service
public class UomService extends BaseService<Uom> {

    public UomService(UomRepo repo) {
        super(repo);
    }
    
}
