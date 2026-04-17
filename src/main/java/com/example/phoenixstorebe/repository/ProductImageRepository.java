package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.Product;
import com.example.phoenixstorebe.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
