package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.entity.Product;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.payload.category.CategoryResponse;
import com.example.phoenixstorebe.payload.product.*;
import com.example.phoenixstorebe.repository.CategoryRepository;
import com.example.phoenixstorebe.repository.ProductRepository;
import com.example.phoenixstorebe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setDeleted(false);
        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        product.setCategories(categories);
        product = productRepository.save(product);
        return toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        try {
            Product product = productRepository.findById(id)
                .orElseThrow();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            product.setCategories(categories);
            product = productRepository.save(product);
            return toResponse(product);
        } catch (Exception ex) {
            throw new BadRequestException("Product not found or update failed");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                .orElseThrow();
            product.setDeleted(true);
            productRepository.save(product);
        } catch (Exception ex) {
            throw new BadRequestException("Product not found or delete failed");
        }
    }

    @Override
    public ProductResponse getProductById(Long id) {
        try {
            Product product = productRepository.findById(id)
                .orElseThrow();
            return toResponse(product);
        } catch (Exception ex) {
            throw new BadRequestException("Product not found");
        }
    }

    @Override
    public List<ProductResponse> searchProducts(ProductSearchRequest request) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(p -> !p.isDeleted())
                .filter(p -> request.getNameProduct() == null || p.getName().toLowerCase().contains(request.getNameProduct().toLowerCase()))
                .filter(p -> request.getCategoryId() == null || p.getCategories().stream().anyMatch(c -> c.getId().equals(request.getCategoryId())))
                .filter(p -> request.getNameCategory() == null || p.getCategories().stream().anyMatch(c -> c.getName().toLowerCase().contains(request.getNameCategory().toLowerCase())))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null || category.getProducts() == null) {
            return List.of();
        }
        return category.getProducts().stream()
                .filter(p -> !p.isDeleted())
                .map(product -> toResponse(product))
                .collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setDeleted(product.isDeleted());
        if (product.getCategories() != null) {

            res.setCategories(product.getCategories().stream().map(category -> {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(category.getId());
                categoryResponse.setName(category.getName());
                return categoryResponse;
            }).collect(Collectors.toList()));

        }
        return res;
    }
}
