package com.example.phoenixstorebe.security;

import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.payload.user.UserReponse;

public interface JwtService {

    String generateToken(User user);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}

