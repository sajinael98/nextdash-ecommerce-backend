package com.saji.dashboard_backend.modules.buying_management.repositories;

import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.buying_management.entities.PurchaseTransaction;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface PurhcaseTransactionRepo extends GenericJpaRepository<PurchaseTransaction, Long> {
    
}
