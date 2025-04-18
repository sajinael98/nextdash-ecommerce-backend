package com.saji.dashboard_backend.modules.stock_management.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface StockRoomRepo extends GenericJpaRepository<StockRoom, Long> {
    Optional<StockRoom> findByItemIdAndWarehouseId(Long itemId, Long warehouseId);
}
