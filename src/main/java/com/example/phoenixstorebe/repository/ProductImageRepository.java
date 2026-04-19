package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {


    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId")
    List<ProductImage> findByProductId(@Param("productId") Long productId);

    @Query("SELECT pi FROM ProductImage pi WHERE pi.variant.Id = :variantId")
    List<ProductImage> findByVariantId(@Param("variantId") Long variantId);
}
