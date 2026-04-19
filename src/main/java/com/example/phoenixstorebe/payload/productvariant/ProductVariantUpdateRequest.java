package com.example.phoenixstorebe.payload.productvariant;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductVariantUpdateRequest {
    @NotNull
    private String sku;
    @NotNull
    @DecimalMin(value = "0", message = "Price phải lớn hơn 0")
    private BigDecimal price;
    @NotNull
    @DecimalMin(value = "0", message = "Stock phải lớn hơn 0")
    private Integer stockQty;
}

