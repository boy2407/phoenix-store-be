package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    @Query("SELECT v FROM ProductVariant v WHERE v.product.id = :productId")
    List<ProductVariant> findByProductId(@Param("productId") Long productId);
}
