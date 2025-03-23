package com.saji.dashboard_backend.modules.stock_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.stock_management.entities.Warehouse;
import com.saji.dashboard_backend.shared.repositories.BaseRepository;

@Repository
public interface WarehouseRepo extends BaseRepository<Warehouse, Long> {

}
