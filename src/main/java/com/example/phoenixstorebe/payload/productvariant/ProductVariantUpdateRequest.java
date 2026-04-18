package com.example.phoenixstorebe.payload.productvariant;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductVariantUpdateRequest {
    private String sku;
    private BigDecimal price;
    private Integer stockQty;
}

