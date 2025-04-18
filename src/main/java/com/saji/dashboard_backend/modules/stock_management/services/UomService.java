package com.saji.dashboard_backend.modules.stock_management.services;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Uom;
import com.saji.dashboard_backend.modules.stock_management.repositories.UomRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

@Service
public class UomService extends BaseServiceImpl<Uom> {

    public UomService(UomRepo repo) {
        super(repo);
    }

}
