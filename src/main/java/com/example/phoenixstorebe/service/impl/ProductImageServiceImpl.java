package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Product;
import com.example.phoenixstorebe.entity.ProductImage;
import com.example.phoenixstorebe.entity.ProductVariant;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.exception.EntityNotFoundException;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final StorageService storageService;

    private ProductImageResponse addProductImage(MultipartFile file, Boolean isMain, Product product, ProductVariant variant, String productName, String sku) {
        String url = storageService.store(file, productName, sku);
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setIsMain(isMain != null ? isMain : false);
        image.setProduct(product);
        image.setVariant(variant);
        return toResponse(productImageRepository.save(image));
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product"));
    }


    private ProductVariant getVariantById(Long id) {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Variant"));
    }


    @Override
    @Transactional
    public Boolean createImagesBatch(ProductImageBatchCreateRequest req) {
        try {
            Product product = null;
            ProductVariant variant = null;
            String productName = null;
            String sku = null;
            if (req.getProductId() != null) {
                product = getProductById(req.getProductId());
                productName = product.getName();
            }
            if (req.getVariantId() != null) {
                variant = getVariantById(req.getVariantId());
                sku = variant.getSku();
            }
            if (req.getImages() != null) {
                for (MultipartFile file : req.getImages()) {
                    addProductImage(file, false, product, variant, productName, sku);
                }
            }
            return true;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating product images batch: " + ex.getMessage(), ex);
        }
    }

    @Override
    @Transactional
    public ProductImageResponse createImages(ProductImageCreateRequest req) {

        try {
            Product product = null;
            ProductVariant variant = null;
            String productName = null;
            String sku = null;
            ProductImageResponse responses = new ProductImageResponse();
            if (req.getProductId() != null) {
                product = getProductById(req.getProductId());
                productName = product.getName();
            }
            if (req.getVariantId() != null) {
                variant = getVariantById(req.getVariantId());
                sku = variant.getSku();
            }
            if (req.getImage() != null) {
                responses = addProductImage(
                    req.getImage(),
                    req.getIsMain(),
                    product,
                    variant,
                    productName,
                    sku
                );
            }
            return responses;
        } catch (Exception ex) {
            throw new BadRequestException("Error creating product image: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductImageResponse updateImage(Long id, ProductImageUpdateRequest request) {

        try {
            ProductImage image = productImageRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Image"));
            String productName = null;
            String sku = null;

            if (request.getProductId() != null) {
                Product product = getProductById(request.getProductId());
                image.setProduct(product);
                productName = product.getName();
            } else {
                image.setProduct(null);
            }
            if (request.getVariantId() != null) {
                ProductVariant variant = getVariantById(request.getVariantId());
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
        } catch (Exception ex) {
            throw new BadRequestException("Error updating product image: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        try {
            ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( "Image"));
            if (image.getImageUrl() != null && !image.getImageUrl().isEmpty()) {
                storageService.delete(image.getImageUrl());
            }
            productImageRepository.deleteById(id);
        } catch (Exception ex) {
            throw new BadRequestException("Error deleting product image: " + ex.getMessage());
        }
    }

    @Override
    public List<ProductImageResponse> getImagesByProduct(Long productId) {
        try {
            return productImageRepository.findByProductId(productId).stream().map(this::toResponse).collect(Collectors.toList());
        } catch (Exception ex) {
              throw  new EntityNotFoundException( "Images");
        }
    }

    @Override
    public List<ProductImageResponse> getImagesByVariant(Long variantId) {
        try {
            return productImageRepository.findByVariantId(variantId).stream().map(this::toResponse).collect(Collectors.toList());
        } catch (Exception ex) {
            throw  new EntityNotFoundException( "Images");
        }
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
