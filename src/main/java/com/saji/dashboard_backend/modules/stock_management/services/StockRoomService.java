package com.saji.dashboard_backend.modules.stock_management.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransaction;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoomLog;
import com.saji.dashboard_backend.modules.stock_management.repositories.StockRoomRepo;

@Service
public class StockRoomService {
    private final StockRoomRepo stockRoomRepo;
    private final JdbcTemplate jdbcTemplate;

    public StockRoomService(StockRoomRepo stockRoomRepo, JdbcTemplate jdbcTemplate) {
        this.stockRoomRepo = stockRoomRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void applyMovement(PurchaseTransaction purchaseTransaction, Long itemId,
            Double quantity) {
        StockRoom stockRoom = stockRoomRepo.findByItemIdAndWarehouseId(itemId, purchaseTransaction.getWarehouseId())
                .orElseGet(() -> createStockRoom(itemId, purchaseTransaction.getWarehouseId()));
        System.out.println(stockRoom);
        StockCommand command = StockCommandFactory.createCommand(
                purchaseTransaction.getTransactionType(), stockRoom, quantity);
        command.execute();

        StockRoomLog log = createLog(purchaseTransaction, quantity, stockRoom);
        stockRoom.getLogs().add(log);

        stockRoomRepo.save(stockRoom);
    }

    public void undoMovement(PurchaseTransaction purchaseTransaction, Long itemId,
            Double quantity) {
        StockRoom stockRoom = stockRoomRepo.findByItemIdAndWarehouseId(itemId, purchaseTransaction.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("StockRoom not found"));

        StockCommand command = StockCommandFactory.createCommand(
                purchaseTransaction.getTransactionType(), stockRoom, quantity);
        command.undo();

        stockRoom.getLogs().removeIf(log -> log.getTransactionId().equals(purchaseTransaction.getId()));

        stockRoomRepo.save(stockRoom);
    }

    private StockRoomLog createLog(PurchaseTransaction tx, Double qty, StockRoom stockRoom) {
        StockRoomLog log = new StockRoomLog();
        log.setTransactionDate(tx.getTransactionDate());
        log.setTransactionId(tx.getId());
        log.setTransactionType(tx.getTransactionType());
        log.setTransactionVoucherType(tx.getClass().getSimpleName());
        log.setQty(qty);
        return log;
    }


    private StockRoom createStockRoom(Long itemId, Long warehouse){
        StockRoom stockRoom = new StockRoom();
        stockRoom.setItemId(itemId);
        stockRoom.setWarehouseId(warehouse);
        stockRoom.setUomId(getUomIdForItem(itemId));
        return stockRoom;
    }

    private Long getUomIdForItem(Long itemId) {
        String sql = """
                    SELECT
                        uomId
                    FROM `res_item_uoms`
                    WHERE
                        itemId = ?
                        AND value = 1
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, itemId);
    }
}
