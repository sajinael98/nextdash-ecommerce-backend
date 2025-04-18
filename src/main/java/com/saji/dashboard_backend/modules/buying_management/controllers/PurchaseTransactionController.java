package com.saji.dashboard_backend.modules.buying_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransaction;
import com.saji.dashboard_backend.modules.buying_management.services.PurchaseTransactionService;
import com.saji.dashboard_backend.shared.controllers.ConfirmableController;

@RestController
@RequestMapping("/purchase-transactions")
public class PurchaseTransactionController extends ConfirmableController<PurchaseTransaction> {

    public PurchaseTransactionController(PurchaseTransactionService service) {
        super(service);
    }
    
}
