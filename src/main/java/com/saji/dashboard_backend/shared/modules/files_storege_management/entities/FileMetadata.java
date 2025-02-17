package com.saji.dashboard_backend.shared.modules.files_storege_management.entities;

import java.time.LocalDateTime;

import com.saji.dashboard_backend.shared.entites.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "files")
@Data
public class FileMetadata extends BaseEntity {
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate;
}