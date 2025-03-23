package com.saji.dashboard_backend.modules.stock_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Warehouse;
import com.saji.dashboard_backend.modules.stock_management.repositories.WarehouseRepo;
import com.saji.dashboard_backend.shared.services.BaseService;

@Service
public class WarehouseService extends BaseService<Warehouse> {

    public WarehouseService(WarehouseRepo repo) {
        super(repo);
    }

}
