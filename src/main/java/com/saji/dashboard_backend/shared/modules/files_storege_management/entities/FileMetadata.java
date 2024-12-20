package com.saji.dashboard_backend.shared.modules.files_storege_management.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

// @Entity
// @Table(name = "files")
@Data
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(name = "upload_date", nullable = false, updatable = false)
    private LocalDateTime uploadDate;
}