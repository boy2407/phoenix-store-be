package com.example.phoenixstorebe.controller;

import com.example.phoenixstorebe.payload.productvariant.ProductVariantCreateRequest;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantUpdateRequest;
import com.example.phoenixstorebe.payload.productvariant.ProductVariantResponse;
import com.example.phoenixstorebe.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductVariantController {
    @Autowired
    private ProductVariantService productVariantService;

    @PostMapping("/product-variants")
    public ResponseEntity<ProductVariantResponse> create(@RequestBody ProductVariantCreateRequest request) {
        return ResponseEntity.ok(productVariantService.createProductVariant(request));
    }

    @PutMapping("/product-variants/{id}")
    public ResponseEntity<ProductVariantResponse> update(@PathVariable Long id, @RequestBody ProductVariantUpdateRequest request) {
        return ResponseEntity.ok(productVariantService.updateProductVariant(id, request));
    }

    @GetMapping("/products/{productId}/variants")
    public ResponseEntity<List<ProductVariantResponse>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productVariantService.getProductVariantsByProductId(productId));
    }
}

