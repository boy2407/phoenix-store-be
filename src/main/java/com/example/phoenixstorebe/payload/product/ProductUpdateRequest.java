package com.example.phoenixstorebe.payload.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class ProductUpdateRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private List<Long> categoryIds;
}

