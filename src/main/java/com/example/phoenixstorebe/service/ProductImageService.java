package com.example.phoenixstorebe.service;

import com.example.phoenixstorebe.payload.productimage.ProductImageBatchCreateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageCreateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageUpdateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageResponse;

import java.util.List;

public interface ProductImageService {
    ProductImageResponse createImages(ProductImageCreateRequest request);
    Boolean createImagesBatch(ProductImageBatchCreateRequest request);
    ProductImageResponse updateImage(Long id, ProductImageUpdateRequest request);
    void deleteImage(Long id);
    List<ProductImageResponse> getImagesByProduct(Long productId);
    List<ProductImageResponse> getImagesByVariant(Long variantId);
}
