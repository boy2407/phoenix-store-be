package com.example.phoenixstorebe.repository;

import com.example.phoenixstorebe.entity.Cart;
import com.example.phoenixstorebe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}

