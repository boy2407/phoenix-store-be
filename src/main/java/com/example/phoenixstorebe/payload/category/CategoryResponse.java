package com.example.phoenixstorebe.payload.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
}

