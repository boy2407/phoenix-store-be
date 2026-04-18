package com.example.phoenixstorebe.config;

import com.example.phoenixstorebe.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return args -> {
            try {
                storageService.init();
                System.out.println(">>>Storage initialization success !");
            } catch (Exception e) {
                System.err.println(">>> Storage initialization error: " + e.getMessage());
            }
        };
    }
}