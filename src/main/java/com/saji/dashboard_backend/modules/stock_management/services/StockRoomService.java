package com.saji.dashboard_backend.modules.stock_management.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoomLog;
import com.saji.dashboard_backend.modules.stock_management.repositories.StockRoomRepo;

import jakarta.transaction.Transactional;

@Service
public class StockRoomService {
    private final StockRoomRepo stockRoomRepo;
    private final JdbcTemplate jdbcTemplate;

    public StockRoomService(StockRoomRepo stockRoomRepo, JdbcTemplate jdbcTemplate) {
        this.stockRoomRepo = stockRoomRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    public StockRoom getOrCreateStockRoom(Long itemId, Long warehouseId) {
        return stockRoomRepo.findByItemIdAndWarehouseId(warehouseId, itemId)
                .orElseGet(() -> {
                    StockRoom stockRoom = createNewStockRoom(itemId, warehouseId);
                    return stockRoomRepo.save(stockRoom);
                });
    }

    private StockRoom createNewStockRoom(Long itemId, Long warehouseId) {
        Long uomId = getUomIdForItem(itemId);

        StockRoom newStockRoom = new StockRoom();
        newStockRoom.setItemId(itemId);
        newStockRoom.setWarehouseId(warehouseId);
        newStockRoom.setUomId(uomId);
        return newStockRoom;
    }

    private Long getUomIdForItem(Long itemId) {
        String sql = """
                    SELECT
                        uomId
                    FROM `res_item_uoms`
                    WHERE
                        itemId = :itemId
                        AND value = 1 
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        return jdbcTemplate.queryForObject(sql, Long.class, params);
    }

    @Transactional
    public void addLogToStockRoom(Long stockRoomId, StockRoomLog log) {
        StockRoom stockRoom = stockRoomRepo.findById(stockRoomId)
                .orElseThrow(() -> new RuntimeException("StockRoom not found"));

        stockRoom.addLog(log);
        stockRoom.setLastModifiedDate(LocalDateTime.now());
        stockRoomRepo.save(stockRoom);
    }

    @Transactional
    public void removeLogFromStockRoom(Long stockRoomId, Long transactionId) {
        StockRoom stockRoom = stockRoomRepo.findById(stockRoomId)
                .orElseThrow(() -> new RuntimeException("StockRoom not found"));

        stockRoom.removeLog(transactionId);
        stockRoomRepo.save(stockRoom);
    }
}
