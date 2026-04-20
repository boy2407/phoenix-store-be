package com.example.phoenixstorebe.controller;

import com.example.phoenixstorebe.entity.Cart;
import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.payload.cart.CartItemCreateRequest;
import com.example.phoenixstorebe.service.CartService;
import com.example.phoenixstorebe.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof User user) {
            return user;
        }
        if (principal instanceof String username && !"anonymousUser".equals(username)) {
            return userService.findUserEntityByUsername(username);
        }
        return null;
    }
    @GetMapping
    public ResponseEntity<Cart> getCart(HttpSession session) {
        User user = getCurrentUser();
        Cart cart = cartService.getCart(user, session);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/item")
    public ResponseEntity<?> addOrUpdateItem(@RequestBody CartItemCreateRequest request, HttpSession session) {
        User user = getCurrentUser();
        cartService.addOrUpdateItem(request, user, session);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeItem(@RequestParam Long cartItemId, HttpSession session) {
        User user = getCurrentUser();
        cartService.removeItem(cartItemId, user, session);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        User user = getCurrentUser();
        cartService.clearCart(user, session);
        return ResponseEntity.ok().build();
    }
}
