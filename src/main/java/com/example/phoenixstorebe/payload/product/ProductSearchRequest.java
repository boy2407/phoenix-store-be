package com.example.phoenixstorebe.payload.product;

import lombok.Data;
import java.util.List;

@Data
public class ProductSearchRequest {
    private String nameProduct;
    private Long categoryId;
    private String nameCategory;
}

