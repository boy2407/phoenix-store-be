package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Product;
import com.example.phoenixstorebe.entity.ProductVariant;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantCreateRequest;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantUpdateRequest;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantResponse;
import com.example.phoenixstorebe.repository.ProductVariantRepository;
import com.example.phoenixstorebe.repository.ProductRepository;
import com.example.phoenixstorebe.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductVariantResponse createProductVariant(ProductVariantCreateRequest request) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow();
            ProductVariant variant = new ProductVariant();
            variant.setSku(request.getSku());
            variant.setPrice(request.getPrice());
            variant.setStockQty(request.getStockQty());
            variant.setProduct(product);
            ProductVariant saved = productVariantRepository.save(variant);
            return toResponse(saved);
        } catch (Exception ex) {
            throw new BadRequestException("Product not found or create failed");
        }
    }

    @Override
    public ProductVariantResponse updateProductVariant(Long id, ProductVariantUpdateRequest request) {
        try {
            ProductVariant variant = productVariantRepository.findById(id)
                    .orElseThrow();
            variant.setSku(request.getSku());
            variant.setPrice(request.getPrice());
            variant.setStockQty(request.getStockQty());
            ProductVariant saved = productVariantRepository.save(variant);
            return toResponse(saved);
        } catch (Exception ex) {
            throw new BadRequestException("ProductVariant not found or update failed");
        }
    }

    @Override
    public List<ProductVariantResponse> getProductVariantsByProductId(Long productId) {
        List<ProductVariant> variants = productVariantRepository.findByProductId(productId);
        return variants.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private ProductVariantResponse toResponse(ProductVariant variant) {
        ProductVariantResponse resp = new ProductVariantResponse();
        resp.setId(variant.getId());
        resp.setSku(variant.getSku());
        resp.setPrice(variant.getPrice());
        resp.setStockQty(variant.getStockQty());
        return resp;
    }
}
