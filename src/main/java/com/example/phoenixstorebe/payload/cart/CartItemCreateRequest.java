package com.example.phoenixstorebe.payload.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemCreateRequest {
    @NotNull
    private Long variantId;
    @NotNull
    @Min(1)
    private int quantity;
}
