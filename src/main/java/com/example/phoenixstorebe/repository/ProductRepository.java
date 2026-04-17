package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
