package com.example.phoenixstorebe.payload.productimage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class ProductImageBatchCreateRequest {
    @Schema(type = "integer", format = "int64")
    private Long productId;
    @Schema(type = "integer", format = "int64")
    private Long variantId;
    @Schema(type = "array", format = "binary", description = "Danh sách file ảnh")
    private List<MultipartFile> images;
}
