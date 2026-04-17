package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
