package com.example.phoenixstorebe.payload.productimage;

import lombok.Data;
import java.util.List;

@Data
public class ProductImageUploadList {
    private List<ProductImageCreateRequest> requests;
}