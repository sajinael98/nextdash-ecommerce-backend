package com.saji.dashboard_backend.shared.modules.files_storege_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saji.dashboard_backend.shared.modules.files_storege_management.entities.FileMetadata;

// @Repository
public interface FileMetadataRepo{
    boolean existsByFileName(String fileName);
}
