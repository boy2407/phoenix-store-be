package com.example.phoenixstorebe.service;

import com.example.phoenixstorebe.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Integer id);
    Role createRole(String nameRole);
    Role updateRole(Integer id, Role role);
    Boolean deleteRole(Integer id);
}
