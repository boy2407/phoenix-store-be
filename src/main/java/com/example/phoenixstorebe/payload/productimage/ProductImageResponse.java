package com.example.phoenixstorebe.payload.productimage;

import lombok.Data;

import java.util.List;

@Data
public class ProductImageResponse {
    private Long id;
    private String imageUrl;
    private Boolean isMain;
    private Long productId;
    private Long variantId;
}
