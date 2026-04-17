package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.ProductImage;
import com.example.phoenixstorebe.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}
