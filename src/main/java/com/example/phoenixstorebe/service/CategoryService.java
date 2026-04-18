package com.example.phoenixstorebe.service;

import com.example.phoenixstorebe.payload.category.CategoryCreateRequest;
import com.example.phoenixstorebe.payload.category.CategoryUpdateRequest;
import com.example.phoenixstorebe.payload.category.CategoryResponse;
import com.example.phoenixstorebe.payload.category.CategoryWithChildrenResponse;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    List<CategoryResponse> getRootCategories();
    List<CategoryWithChildrenResponse> getRootCategoriesWithChildren();
    Optional<CategoryResponse> getCategoryById(Long id);
    CategoryResponse createCategory(CategoryCreateRequest request);
    Optional<CategoryResponse> updateCategory(Long id, CategoryUpdateRequest request);
    boolean deleteCategory(Long id);
}
