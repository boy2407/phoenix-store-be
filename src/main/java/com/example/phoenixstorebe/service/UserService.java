package com.example.phoenixstorebe.service;


import com.example.phoenixstorebe.payload.user.UserCreateRequest;
import com.example.phoenixstorebe.payload.user.UserReponse;


public interface UserService {

    UserReponse registerUser(UserCreateRequest request);
    boolean verifyUser(String verifyToken);
    String login(String username, String password);
    UserReponse findByUsername(String username);
    void updateUser(UserReponse user);
    void deleteUser(String username);

}
