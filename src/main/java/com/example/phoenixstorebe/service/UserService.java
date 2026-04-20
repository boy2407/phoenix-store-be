package com.example.phoenixstorebe.service;


import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.payload.user.UserCreateRequest;
import com.example.phoenixstorebe.payload.user.UserReponse;
import jakarta.servlet.http.HttpSession;


public interface UserService {

    UserReponse registerUser(UserCreateRequest request);
    boolean verifyUser(String verifyToken);
    String login(String username, String password, HttpSession session);
    UserReponse findByUsername(String username);
    void updateUser(UserReponse user);
    void deleteUser(String username);
    User findUserEntityByUsername(String username);
    User getCurrentUser();

}
