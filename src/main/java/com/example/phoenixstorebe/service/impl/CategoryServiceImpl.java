package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.repository.CategoryRepository;
import com.example.phoenixstorebe.service.CategoryService;
import com.example.phoenixstorebe.payload.category.*;
import com.example.phoenixstorebe.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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

    @Override
    public Optional<CategoryResponse> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(this::mapToResponse);
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        try {
            Category category = new Category();
            category.setName(request.getName());
            if (request.getParentId() != null) {
                Optional<Category> parentOpt = categoryRepository.findById(request.getParentId());
                if (parentOpt.isPresent()) {
                    category.setParent(parentOpt.get());
                } else {
                    throw new BadRequestException("Parent category not found");
                }
            } else {
                category.setParent(null);
            }
            return mapToResponse(categoryRepository.save(category));
        } catch (Exception ex) {
            throw new BadRequestException("Create category failed");
        }
    }

    @Override
    public Optional<CategoryResponse> updateCategory(Long id, CategoryUpdateRequest request) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setName(request.getName());
                if (request.getParentId() != null) {
                    categoryRepository.findById(request.getParentId()).ifPresent(category::setParent);
                } else {
                    category.setParent(null);
                }
                return Optional.of(mapToResponse(categoryRepository.save(category)));
            } else {
                throw new BadRequestException("Category not found");
            }
        } catch (Exception ex) {
            throw new BadRequestException("Update category failed");
        }
    }

    @Override
    public boolean deleteCategory(Long id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
                return true;
            } else {
                throw new BadRequestException("Category not found");
            }
        } catch (Exception ex) {
            throw new BadRequestException("Delete category failed");
        }
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
