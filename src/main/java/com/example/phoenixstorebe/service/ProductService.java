package com.example.phoenixstorebe.service;

import com.example.phoenixstorebe.payload.product.*;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse updateProduct(Long id, ProductUpdateRequest request);
    void deleteProduct(Long id);
    ProductResponse getProductById(Long id);
    List<ProductResponse> searchProducts(ProductSearchRequest request);
    List<ProductResponse> getProductsByCategoryId(Long categoryId);
}

