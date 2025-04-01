package com.saji.dashboard_backend.modules.stock_management.entities;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ItemWebsiteInformation {
    private boolean published;
    private LocalDate publishedDate;
}
