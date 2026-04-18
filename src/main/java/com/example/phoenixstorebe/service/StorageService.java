package com.example.phoenixstorebe.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file);
    String store(MultipartFile file, String productName, String sku);
    void init();
    void delete(String filePath);
}
