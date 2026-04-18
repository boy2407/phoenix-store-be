package com.example.phoenixstorebe.payload.product;

import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.payload.category.CategoryResponse;
import lombok.Data;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private List<CategoryResponse> categories;
    private boolean isDeleted;
}

