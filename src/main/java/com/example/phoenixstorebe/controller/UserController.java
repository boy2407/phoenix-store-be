package com.example.phoenixstorebe.controller;

import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.payload.user.UserCreateRequest;
import com.example.phoenixstorebe.payload.user.UserReponse;
import com.example.phoenixstorebe.service.UserService;
import com.example.phoenixstorebe.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/register")
    public ResponseEntity<UserReponse> register(@RequestBody @Valid UserCreateRequest request) {
        UserReponse user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("token") String token) {
        boolean result = userService.verifyUser(token);
        if (result) {
            return ResponseEntity.ok("Tài khoản đã được xác thực thành công!");
        } else {
            return ResponseEntity.badRequest().body("Xác thực thất bại hoặc tài khoản đã được xác thực.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        String token = userService.login(username, password, session);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserReponse> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserReponse user) {
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successfully");
    }
}
