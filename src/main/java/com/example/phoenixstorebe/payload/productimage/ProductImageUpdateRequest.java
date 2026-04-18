package com.example.phoenixstorebe.payload.productimage;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductImageUpdateRequest {
    private MultipartFile image; // The uploaded image file (optional, for updating the image)
    private Boolean isMain;
    private Long productId;
    private Long variantId; // optional, can be null
}
