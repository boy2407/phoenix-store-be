package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNull();
    Optional<Category> findByName(String name);
}
