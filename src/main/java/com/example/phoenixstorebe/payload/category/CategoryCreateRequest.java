package com.example.phoenixstorebe.payload.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateRequest {
    private String name;
    private Long parentId;
}

