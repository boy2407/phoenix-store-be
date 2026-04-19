package com.example.phoenixstorebe.payload.productimage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class ProductImageCreateRequest {

    @Schema(type = "array", format = "binary")
    private MultipartFile image; // The uploaded image files
    @NotNull
    private Boolean isMain;
    @NotNull
    private Long productId;
    @NotNull
    private Long variantId; // optional, can be null
}
