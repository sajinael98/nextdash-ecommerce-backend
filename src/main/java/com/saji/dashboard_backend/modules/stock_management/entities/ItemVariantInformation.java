package com.saji.dashboard_backend.modules.stock_management.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Embeddable
public class ItemVariantInformation {
    @Column(columnDefinition = "INT")
    private boolean hasSubItems;

    @Column
    private Long templateId;

    @Column
    private String template;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item_variants", joinColumns = @JoinColumn(name = "itemId", nullable = false))
    private Set<ItemVariant> values = new HashSet<>();
}
