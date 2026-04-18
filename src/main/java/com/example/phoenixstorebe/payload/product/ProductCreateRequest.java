package com.example.phoenixstorebe.payload.product;

import lombok.Data;
import java.util.List;

@Data
public class ProductCreateRequest {
    private String name;
    private String description;
    private List<Long> categoryIds;
}

