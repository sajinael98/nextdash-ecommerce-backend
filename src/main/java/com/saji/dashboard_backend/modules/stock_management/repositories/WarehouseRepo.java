package com.saji.dashboard_backend.modules.stock_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.stock_management.entities.Warehouse;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface WarehouseRepo extends GenericJpaRepository<Warehouse, Long> {

}
