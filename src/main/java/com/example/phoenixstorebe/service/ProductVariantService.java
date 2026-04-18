package com.example.phoenixstorebe.service;

import com.example.phoenixstorebe.payload.productvariant.ProductVariantCreateRequest;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantUpdateRequest;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantResponse;
import java.util.List;

public interface ProductVariantService {
    ProductVariantResponse createProductVariant(ProductVariantCreateRequest request);
    ProductVariantResponse updateProductVariant(Long id, ProductVariantUpdateRequest request);
    List<ProductVariantResponse> getProductVariantsByProductId(Long productId);
}

