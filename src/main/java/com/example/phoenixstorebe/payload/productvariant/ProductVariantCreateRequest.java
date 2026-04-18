package com.example.phoenixstorebe.payload.productvariant;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductVariantCreateRequest {
    private String sku;
    private BigDecimal price;
    private Integer stockQty;
    private Long productId;
}

