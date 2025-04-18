package com.saji.dashboard_backend.shared.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting extends BaseEntity {
    @Column
    private String title;
    
    @Column
    private String data;
}
