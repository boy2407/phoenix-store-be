package com.example.phoenixstorebe.repository;

import com.example.phoenixstorebe.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Có thể bổ sung các phương thức custom nếu cần
}

