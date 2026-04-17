package com.example.phoenixstorebe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_main")
    private Boolean isMain = false;

    // Ảnh thuộc về sản phẩm tổng thể
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Ảnh có thể thuộc về một biến thể cụ thể (có thể NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;
}