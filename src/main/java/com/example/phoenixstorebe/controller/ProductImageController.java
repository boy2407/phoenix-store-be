package com.example.phoenixstorebe.controller;

import com.example.phoenixstorebe.payload.productimage.ProductImageBatchCreateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageCreateRequest;
import com.example.phoenixstorebe.payload.productimage.ProductImageResponse;
import com.example.phoenixstorebe.payload.productimage.ProductImageUpdateRequest;
import com.example.phoenixstorebe.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    //    @PostMapping(value = "/upload-batch", consumes = {"multipart/form-data"})
//    @Operation(summary = "Tải lên nhiều hình ảnh (batch)")
//    public ResponseEntity<Boolean> createImagesBatch(@RequestPart ProductImageBatchCreateRequest request) {
//        return ResponseEntity.ok(productImageService.createImagesBatch(request));
//    }

//    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
//    @Operation(summary = "Tải lên ảnh (single)") // Thêm mô tả cho endpoint
//    public ResponseEntity<ProductImageResponse> createImages(@RequestPart ProductImageCreateRequest request) {
//        return ResponseEntity.ok(productImageService.createImages(request));
//    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @Operation(summary = "Tải lên ảnh (single)") // Thêm mô tả cho endpoint
    public ResponseEntity<ProductImageResponse> createImages(@RequestParam("productId") Long productId,
                                                             @RequestParam(value = "variantId", required = false) Long variantId,
                                                             @RequestParam(value = "isMain", required = false) Boolean isMain,
                                                             @RequestPart("images") MultipartFile image){
        ProductImageCreateRequest request = new ProductImageCreateRequest();
        request.setProductId(productId);
        request.setVariantId(variantId);
        request.setIsMain(isMain);
        request.setImage(image);
        return ResponseEntity.ok(productImageService.createImages(request));
    }


    @PostMapping(value = "/upload-batch", consumes = {"multipart/form-data"})
    @Operation(summary = "Tải lên ảnh nhiều (batch )") // Thêm mô tả cho endpoint
    public ResponseEntity<Boolean> createImagesBatch(@RequestParam("productId") Long productId,
                                                     @RequestParam(value = "variantId", required = false) Long variantId,
                                                     @RequestPart("images") List<MultipartFile> images)
    {
            ProductImageBatchCreateRequest request = new ProductImageBatchCreateRequest();
            request.setProductId(productId);
            request.setVariantId(variantId);
            request.setImages(images);
            return ResponseEntity.ok(productImageService.createImagesBatch(request));
    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductImageResponse> updateImage(@PathVariable Long id, @ModelAttribute ProductImageUpdateRequest request) {
        return ResponseEntity.ok(productImageService.updateImage(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        productImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ProductImageResponse>> getImagesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getImagesByProduct(productId));
    }

    @GetMapping("/by-variant/{variantId}")
    public ResponseEntity<List<ProductImageResponse>> getImagesByVariant(@PathVariable Long variantId) {
        return ResponseEntity.ok(productImageService.getImagesByVariant(variantId));
    }
}
