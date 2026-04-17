package com.example.phoenixstorebe.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;



@Entity
@Setter
@Getter
@Table(name = "Categories")
public class Category {
    @Id
    private Long id;

    @Column(name = "name", length = 255)
    private  String name;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private  Category parent;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
