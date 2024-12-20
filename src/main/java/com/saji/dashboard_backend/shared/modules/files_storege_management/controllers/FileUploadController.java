package com.saji.dashboard_backend.shared.modules.files_storege_management.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saji.dashboard_backend.shared.modules.files_storege_management.services.FileStorageService;

import lombok.RequiredArgsConstructor;

// @RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileUploadController {

    // private final FileStorageService fileStorageService;

    // @PostMapping("/upload")
    // public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    //     try {
    //         return ResponseEntity.ok(fileStorageService.storeFile(file));
    //     } catch (IOException e) {
    //         return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
    //     }
    // }

    // @GetMapping("/{filename}")
    // public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
    //     try {
    //         return ResponseEntity.ok()
    //                 .contentType(MediaType.IMAGE_JPEG) // Adjust content type based on image format
    //                 .body(fileStorageService.getFile(filename));
    //     } catch (IOException e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}