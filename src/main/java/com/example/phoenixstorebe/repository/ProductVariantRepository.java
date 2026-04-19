package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);
}
