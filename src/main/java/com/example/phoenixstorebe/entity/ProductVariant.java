package com.example.phoenixstorebe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "product_variants")
public class ProductVariant {

    @Id
    @GeneratedValue( strategy = jakarta.persistence.GenerationType.IDENTITY)
    private  Long Id;

    @Column(unique = true, length = 100)
    private String sku;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_qty")
    private Integer stockQty = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private List<ProductImage> variantImages;
}
