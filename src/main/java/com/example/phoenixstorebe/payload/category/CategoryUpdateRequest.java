package com.example.phoenixstorebe.payload.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {
    @NotNull
    private String name;
    private Long parentId;
}

