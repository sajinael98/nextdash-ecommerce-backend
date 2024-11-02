package com.saji.dashboard_backend.shared.modules.files_storege_management.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saji.dashboard_backend.shared.modules.files_storege_management.entities.FileMetadata;
import com.saji.dashboard_backend.shared.modules.files_storege_management.repositories.FileMetadataRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileMetadataRepo fileMetadataRepository;

    public String storeFile(MultipartFile file) throws IOException {
        // Create the upload directory if it does not exist
        Path directoryPath = Paths.get(uploadDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath); // Create the directory
        }

        // Create a new file in the upload directory
        File newFile = new File(directoryPath.toFile(), file.getOriginalFilename());

        // Check if the file already exists and handle accordingly
        if (newFile.exists()) {
            String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            newFile = new File(directoryPath.toFile(), newFileName);
        }

        // Write the file content using InputStream
        InputStream inputStream = file.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(newFile);

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Create and save file metadata
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileType(file.getContentType());
        metadata.setFileSize(file.getSize());
        metadata.setUploadDate(LocalDateTime.now());

        fileMetadataRepository.save(metadata);

        return newFile.getAbsolutePath();
    }

    public byte[] getFile(String filename) throws IOException {
        if (!fileMetadataRepository.existsByFileName(filename)) {
            throw new EntityNotFoundException("file with name: " + filename + " is not found.");
        }
        
        Path imagePath = Paths.get(uploadDir, filename);
        byte[] imageData = Files.readAllBytes(imagePath);
        return imageData;
    }
}