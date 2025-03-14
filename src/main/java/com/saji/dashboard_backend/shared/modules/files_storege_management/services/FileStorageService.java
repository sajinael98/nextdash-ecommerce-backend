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
import jakarta.transaction.Transactional;

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
        String fileName = renameFile(file);
        // Create a new file in the upload directory
        File newFile = new File(directoryPath.toFile(), fileName);

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
        metadata.setFileName(fileName);
        metadata.setFileType(file.getContentType());
        metadata.setFileSize(file.getSize());
        metadata.setUploadDate(LocalDateTime.now());

        fileMetadataRepository.save(metadata);

        return fileName;
    }

    public byte[] getFile(String filename) throws IOException {
        if (!fileMetadataRepository.existsByFileName(filename)) {
            throw new EntityNotFoundException("file with name: " + filename + " is not found.");
        }

        Path imagePath = Paths.get(uploadDir, filename);
        byte[] imageData = Files.readAllBytes(imagePath);
        return imageData;
    }

    @Transactional
    public void removeFile(String filename) throws IOException {
        // Check if the file metadata exists
        if (!fileMetadataRepository.existsByFileName(filename)) {
            throw new EntityNotFoundException("File with name: " + filename + " is not found.");
        }

        // Construct the path to the file
        Path filePath = Paths.get(uploadDir, filename);

        // Attempt to delete the file
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new IOException("Failed to delete file: " + filename, e);
        }

        // Remove the file metadata from the repository
        fileMetadataRepository.deleteByFileName(filename);
    }

    public static String renameFile(MultipartFile multipartFile) throws IOException {
        // Get the original file name and extension
        String originalFileName = multipartFile.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }

        // Get the base name without extension
        String baseName = originalFileName.substring(0, dotIndex > 0 ? dotIndex : originalFileName.length());

        // Generate a unique identifier (timestamp)
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());

        // Create the new file name
        String newFileName = baseName + "_" + uniqueSuffix + extension;

        // Check for conflicts (in the current directory)
        Path newPath = Paths.get(newFileName);
        int counter = 1;
        while (Files.exists(newPath)) {
            newFileName = baseName + "_" + uniqueSuffix + "_" + counter + extension;
            newPath = Paths.get(newFileName);
            counter++;
        }

        // Return the final unique file name
        return newPath.toString();
    }
}