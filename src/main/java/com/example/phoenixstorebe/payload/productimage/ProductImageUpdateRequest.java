package com.example.phoenixstorebe.payload.productimage;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductImageUpdateRequest {
    @NotNull
    private MultipartFile image; // The uploaded image file (optional, for updating the image)
    @NotNull
    private Boolean isMain;
    @NotNull
    private Long productId;
    @NotNull
    private Long variantId; // optional, can be null
}
