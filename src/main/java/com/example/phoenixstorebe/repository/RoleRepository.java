package com.example.phoenixstorebe.repository;


import com.example.phoenixstorebe.entity.Category;
import com.example.phoenixstorebe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
