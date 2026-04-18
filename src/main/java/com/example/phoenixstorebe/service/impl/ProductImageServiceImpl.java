package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Product;
import com.example.phoenixstorebe.entity.ProductImage;
import com.example.phoenixstorebe.entity.ProductVariant;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.payload.productimage.ProductImageBatchCreateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageCreateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageResponse;
import com.example.phoenixstorebe.payload.productimage.ProductImageUpdateRequest;
import com.example.phoenixstorebe.repository.ProductImageRepository;
import com.example.phoenixstorebe.repository.ProductRepository;
import com.example.phoenixstorebe.repository.ProductVariantRepository;
import com.example.phoenixstorebe.service.ProductImageService;
import com.example.phoenixstorebe.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public ProductImageResponse createImages(ProductImageCreateRequest req) {

        Product product = null;
        ProductVariant variant = null;
        String productName = null;
        String sku = null;
        ProductImageResponse responses = new ProductImageResponse();
        if (req.getProductId() != null) {
            product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new BadRequestException("Product not found"));
            productName = product.getName();
        }
        if (req.getVariantId() != null) {
            variant = productVariantRepository.findById(req.getVariantId())
                    .orElseThrow(() -> new BadRequestException("Variant not found"));
            sku = variant.getSku();
        }
        if (req.getImage() != null) {
            String url = storageService.store(req.getImage(), productName, sku);
            ProductImage image = new ProductImage();
            image.setImageUrl(url);
            image.setIsMain(req.getIsMain() != null ? req.getIsMain() : false);
            image.setProduct(product);
            image.setVariant(variant);
            responses = (toResponse(productImageRepository.save(image)));
        }

        return responses;
    }

    @Override
    @Transactional
    public ProductImageResponse updateImage(Long id, ProductImageUpdateRequest request) {

        ProductImage image = productImageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        String productName = null;
        String sku = null;

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));
            image.setProduct(product);
            productName = product.getName();
        } else {
            image.setProduct(null);
        }
        if (request.getVariantId() != null) {
            ProductVariant variant = productVariantRepository.findById(request.getVariantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Variant not found"));
            image.setVariant(variant);
            sku = variant.getSku();
        } else {
            image.setVariant(null);
        }
        if (request.getImage() != null && !request.getImage().isEmpty()) {

            MultipartFile file = request.getImage();
            String url = storageService.store(file, productName, sku);
            image.setImageUrl(url);
        }
        image.setIsMain(request.getIsMain() != null ? request.getIsMain() : false);
        return toResponse(productImageRepository.save(image));
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        ProductImage image = productImageRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        if (image.getImageUrl() != null && !image.getImageUrl().isEmpty()) {
            storageService.delete(image.getImageUrl());
        }
        productImageRepository.deleteById(id);
    }

    @Override
    public List<ProductImageResponse> getImagesByProduct(Long productId) {
        return productImageRepository.findByProductId(productId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductImageResponse> getImagesByVariant(Long variantId) {
        return productImageRepository.findByVariantId(variantId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean createImagesBatch(ProductImageBatchCreateRequest req) {
        Product product = null;
        ProductVariant variant = null;
        String productName = null;
        String sku = null;
        if (req.getProductId() != null) {
            product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Product not found"));
            productName = product.getName();
        }
        if (req.getVariantId() != null) {
            variant = productVariantRepository.findById(req.getVariantId())
                    .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Variant not found"));
            sku = variant.getSku();
        }
        java.util.List<String> urls = new java.util.ArrayList<>();
        if (req.getImages() != null) {
            for (org.springframework.web.multipart.MultipartFile file : req.getImages()) {
                String url = storageService.store(file, productName, sku);
                ProductImage image = new ProductImage();
                image.setImageUrl(url);
                image.setProduct(product);
                image.setVariant(variant);
                productImageRepository.save(image);
                urls.add(url);
            }
        }
        return true;
    }

    private ProductImageResponse toResponse(ProductImage image) {
        ProductImageResponse res = new ProductImageResponse();
        res.setId(image.getId());
        res.setImageUrl(image.getImageUrl());
        res.setIsMain(image.getIsMain());
        res.setProductId(image.getProduct() != null ? image.getProduct().getId() : null);
        res.setVariantId(image.getVariant() != null ? image.getVariant().getId() : null);
        return res;
    }
}
