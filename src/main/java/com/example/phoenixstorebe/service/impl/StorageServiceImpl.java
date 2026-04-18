package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;


@Service
public class StorageServiceImpl implements StorageService {
    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Không thể khởi tạo thư mục lưu trữ", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) throw new RuntimeException("File is empty !");

            // Tạo tên file duy nhất để tránh ghi đè (Dùng UUID)
            LocalDate nowday = LocalDate.now();
            String year = String.valueOf(nowday.getYear());
            String month = String.format("%02d", nowday.getMonthValue());

            Path uploadPath = this.rootLocation.resolve(year).resolve(month);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return year + "/" + month + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error when saving a file", e);
        }
    }

    // Utility: Remove Vietnamese diacritics
    private String removeVietnameseDiacritics(String input) {
        if (input == null) return null;
        String temp = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        temp = temp.replaceAll("Đ", "D").replaceAll("đ", "d");
        return temp;
    }

    @Override
    public String store(MultipartFile file, String productName, String sku) {
        try {
            if (file.isEmpty()) throw new RuntimeException("File is empty !");

            LocalDate nowday = LocalDate.now();
            String year = String.valueOf(nowday.getYear());
            String month = String.format("%02d", nowday.getMonthValue());

            // Normalize productName and sku for folder names
            String safeProductName = productName != null ? removeVietnameseDiacritics(productName).replaceAll("[^a-zA-Z0-9_-]", "_") : "unknown";
            String safeSku = sku != null ? sku.replaceAll("[^a-zA-Z0-9_-]", "_") : "unknown";

            Path uploadPath = this.rootLocation.resolve(year).resolve(month).resolve(safeProductName).resolve(safeSku);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = uploadPath.resolve(fileName).normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            // Return relative path for DB
            return year + "/" + month + "/" + safeProductName + "/" + safeSku + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file", e);
        }
    }


    public List<String> store(List<MultipartFile> files) {
        List<String> urls = new java.util.ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(store(file));
        }
        return urls;
    }

    @Override
    public void delete(String filePath) {
        try {
            Path file = rootLocation.resolve(filePath).normalize().toAbsolutePath();
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error when deleting a file", e);
        }
    }
}
