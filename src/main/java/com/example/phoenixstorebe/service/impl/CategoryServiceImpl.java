package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.exception.EntityNotFoundException;
import com.example.phoenixstorebe.repository.CategoryRepository;
import com.example.phoenixstorebe.service.CategoryService;
import com.example.phoenixstorebe.payload.category.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse res = new CategoryResponse();
        res.setId(category.getId());
        res.setName(category.getName());
        if (category.getParent() != null) {
            res.setParentId(category.getParent().getId());
            res.setParentName(category.getParent().getName());
        }
        return res;
    }

    private CategoryWithChildrenResponse mapToWithChildren(Category category) {
        CategoryWithChildrenResponse res = new CategoryWithChildrenResponse();
        res.setId(category.getId());
        res.setName(category.getName());
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            res.setChildren(category.getChildren().stream().map(this::mapToWithChildren).collect(Collectors.toList()));
        }
        return res;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findByParentIsNull().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private EntityNotFoundException throwCategoryNotFound() {
        return new EntityNotFoundException("Category");
    }
    private EntityNotFoundException throwParentCategoryNotFound() {
        return new EntityNotFoundException("Parent category");
    }

    @Override
    public Optional<CategoryResponse> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(this::throwCategoryNotFound);
        return Optional.of(mapToResponse(category));
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        try
        {
            Category category = new Category();
            category.setName(request.getName());
            if (request.getParentId() != null) {
                Category parent = categoryRepository.findById(request.getParentId())
                        .orElseThrow(this::throwParentCategoryNotFound);
                category.setParent(parent);
            } else {
                category.setParent(null);
            }
            return mapToResponse(categoryRepository.save(category));
        }
        catch (Exception ex)
        {
            throw new BadRequestException("Error creating category: ");
        }

    }

    @Override
    public Optional<CategoryResponse> updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(this::throwCategoryNotFound);
        category.setName(request.getName());
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(this::throwParentCategoryNotFound);
            category.setParent(parent);
        } else {
            category.setParent(null);
        }
        return Optional.of(mapToResponse(categoryRepository.save(category)));
    }

    @Override
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw throwCategoryNotFound();
        }
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CategoryWithChildrenResponse> getRootCategoriesWithChildren() {
        List<Category> roots = categoryRepository.findByParentIsNull();
        List<CategoryWithChildrenResponse> result = new java.util.ArrayList<>();
        for (Category root : roots) {
            result.add(mapToWithChildren(root));
        }
        return result;
    }
}
