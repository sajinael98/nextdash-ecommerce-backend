package com.saji.dashboard_backend.modules.buying_management.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransaction;
import com.saji.dashboard_backend.modules.buying_management.repositories.PurhcaseTransactionRepo;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoom;
import com.saji.dashboard_backend.modules.stock_management.entities.StockRoomLog;
import com.saji.dashboard_backend.modules.stock_management.services.ItemService;
import com.saji.dashboard_backend.modules.stock_management.services.StockRoomService;
import com.saji.dashboard_backend.modules.stock_management.services.UomService;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;
import com.saji.dashboard_backend.shared.specifications.QueryCriteriaBuilder;

import jakarta.transaction.Transactional;

@Service
public class PurchaseTransactionService extends BaseServiceImpl<PurchaseTransaction> {

    private StockRoomService stockRoomService;

    private ItemService itemService;

    private UomService uomService;

    public PurchaseTransactionService(PurhcaseTransactionRepo repo, StockRoomService stockRoomService,
            ItemService itemService, UomService uomService) {
        super(repo);
        this.stockRoomService = stockRoomService;
        this.itemService = itemService;
        this.uomService = uomService;
    }

    @Override
    @Transactional
    public void afterConfirm(PurchaseTransaction entity) {
        entity.getItems().forEach(item -> {
            final Long itemId = item.getItemId();
            stockRoomService.applyMovement(entity, itemId, item.getStockQty());
        });
    }

    @Override
    public void afterCancel(PurchaseTransaction entity) {
        entity.getItems().forEach(item -> {
            final Long itemId = item.getItemId();
            stockRoomService.undoMovement(entity, itemId, item.getStockQty());
        });
    }

    @Override
    public void beforeSave(PurchaseTransaction entity) {
        entity.getItems().forEach(item -> {
            var stockQty = item.getQty() * item.getUomFactor();
            item.setStockQty(stockQty);

            var total = item.getQty() * item.getPrice();
            item.setTotal(total);
            if (Objects.isNull(item.getItem())) {
                var filters = QueryCriteriaBuilder.builder().add("id", "eq", item.getItemId().toString())
                        .buildFilters();
                var itemName = itemService.getList(List.of("title"), filters).getData().get(0).get("title");
                item.setItem((String) itemName);
            }

            if (Objects.isNull(item.getUom())) {
                var filters = QueryCriteriaBuilder.builder().add("id", "eq", item.getUomId().toString())
                        .buildFilters();
                var uom = uomService.getList(List.of("uom"), filters).getData().get(0).get("uom");
                item.setUom((String) uom);
            }
        });
    }
}
