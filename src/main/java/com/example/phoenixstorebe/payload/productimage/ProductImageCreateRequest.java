package com.example.phoenixstorebe.payload.productimage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductImageCreateRequest {
    @Schema(type = "array", format = "binary")
    private MultipartFile image; // The uploaded image files
    private Boolean isMain;
    private Long productId;
    private Long variantId; // optional, can be null
}
