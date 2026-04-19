package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Role;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.exception.EntityNotFoundException;
import com.example.phoenixstorebe.repository.RoleRepository;
import com.example.phoenixstorebe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Integer id) {

        Role existing = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Role"));
        return existing;
    }

    @Override
    public Role createRole(String nameRole) {
        if (roleRepository.existsByRoleNameIgnoreCase(nameRole)) {
            throw new BadRequestException("Role name already exists: " + nameRole);
        }
        try {
            Role role = new Role();

            role.setRoleName(nameRole.toUpperCase());
            return roleRepository.save(role);
        } catch (Exception e) {
            throw new BadRequestException("Create role failed: " + e.getMessage());
        }

    }

    @Override
    public Role updateRole(Integer id, Role role) {
        try
        {
            Role existing = roleRepository.findById(id).orElse(null);
            if (existing == null) return null;
            existing.setRoleName(role.getRoleName());
            return roleRepository.save(existing);
        }
        catch (Exception e) {
            throw new BadRequestException("Update role failed: " + e.getMessage());
        }

    }

    @Override
    public Boolean deleteRole(Integer id) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Role"));
        roleRepository.delete(existing);
        return true;
    }
}

