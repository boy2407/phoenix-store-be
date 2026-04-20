package com.example.phoenixstorebe.service;

import com.example.phoenixstorebe.entity.Cart;
import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.payload.cart.CartItemCreateRequest;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(User user, HttpSession session);
    void addOrUpdateItem(CartItemCreateRequest request, User user, HttpSession session);
    void removeItem(Long cartItemId, User user, HttpSession session);
    void clearCart(User user, HttpSession session);

    /**
     * Merge Cart từ session vào DB khi login
     */
    void mergeCartSessionToDb(User user, HttpSession session);

}
