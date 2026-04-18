package com.example.phoenixstorebe.payload.product;

import lombok.Data;
import java.util.List;

@Data
public class ProductUpdateRequest {
    private String name;
    private String description;
    private List<Long> categoryIds;
}

