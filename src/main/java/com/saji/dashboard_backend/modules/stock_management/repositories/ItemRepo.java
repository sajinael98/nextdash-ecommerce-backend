package com.saji.dashboard_backend.modules.stock_management.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.modules.stock_management.entities.Item;
import com.saji.dashboard_backend.shared.repositories.base.GenericJpaRepository;

@Repository
public interface ItemRepo extends GenericJpaRepository<Item, Long> {
    @Query(value = "SELECT u.uomId as value, u.uom as label FROM res_item_uoms u WHERE u.itemId = :itemId", nativeQuery = true)
    List<Map<String, Object>> getItemUoms(@Param("itemId") Long itemId);

    @Query(value = "SELECT u.value FROM res_item_uoms u WHERE u.itemId = :itemId AND u.uomId = :uomId", nativeQuery = true)
    Double getItemUomFactor(@Param("itemId") Long itemId, @Param("uomId") Long uomId );
}
