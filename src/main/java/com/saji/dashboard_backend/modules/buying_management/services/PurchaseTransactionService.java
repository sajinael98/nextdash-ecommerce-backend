package com.saji.dashboard_backend.modules.buying_management.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransaction;
import com.saji.dashboard_backend.modules.buying_management.repositories.PurhcaseTransactionRepo;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoomLog;
import com.saji.dashboard_backend.modules.stock_management.services.StockRoomService;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class PurchaseTransactionService extends BaseServiceImpl<PurchaseTransaction> {

    private StockRoomService stockRoomService;

    public PurchaseTransactionService(PurhcaseTransactionRepo repo, StockRoomService stockRoomService) {
        super(repo);
        this.stockRoomService = stockRoomService;
    }

    @Override
    @Transactional
    public void afterConfirm(PurchaseTransaction entity) {
        entity.getItems().forEach(item -> {
            final Long itemId = item.getItemId();
            StockRoom stockRoom = stockRoomService.getOrCreateStockRoom(itemId, entity.getWarehouseId());

            StockRoomLog log = new StockRoomLog();
            log.setTransactionId(entity.getId());
            log.setTransactionDate(LocalDateTime.now());
            log.setTransactionType(entity.getTransactionType());
            log.setQty(item.getQty() * item.getUomFactor());

            stockRoomService.addLogToStockRoom(stockRoom.getId(), log);
        });
    }

    @Override
    public void afterCancel(PurchaseTransaction entity) {
        entity.getItems().forEach(item -> {
            final Long itemId = item.getItemId();
            StockRoom stockRoom = stockRoomService.getOrCreateStockRoom(itemId, entity.getWarehouseId());

            stockRoomService.removeLogFromStockRoom(stockRoom.getId(), entity.getId());
        });
    }
}
