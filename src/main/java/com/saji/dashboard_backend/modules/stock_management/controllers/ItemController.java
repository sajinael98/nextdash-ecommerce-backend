package com.saji.dashboard_backend.modules.stock_management.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.stock_management.entities.Item;
import com.saji.dashboard_backend.modules.stock_management.services.ItemService;
import com.saji.dashboard_backend.shared.controllers.BaseController;

@RestController
@RequestMapping("/items")
public class ItemController extends BaseController<Item> {

    private ItemService service;

    public ItemController(ItemService service) {
        super(service);
        this.service = service;
    }

    @PostMapping("/{itemId}/sub-items")
    public ResponseEntity<Void> createSubItems(@PathVariable(required = true) Long itemId,
            @RequestBody Map<String, String[]> variantValues) {
        service.createSubItems(itemId, variantValues);
        return ResponseEntity.ok().build();
    }
}
