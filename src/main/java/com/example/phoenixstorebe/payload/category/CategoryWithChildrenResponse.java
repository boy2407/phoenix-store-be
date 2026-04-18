package com.example.phoenixstorebe.payload.category;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CategoryWithChildrenResponse {
    private Long id;
    private String name;
    private List<CategoryWithChildrenResponse> children;
}

