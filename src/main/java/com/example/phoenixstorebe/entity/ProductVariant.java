package com.example.phoenixstorebe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "product_variants")
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductImage> variantImages;
}
