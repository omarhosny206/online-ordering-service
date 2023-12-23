package com.example.service.impl;

import com.example.dto.RoleDto;
import com.example.entity.Role;
import com.example.repository.RoleRepository;
import com.example.service.RoleService;
import com.example.util.ApiError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(long id) {
        return roleRepository.findById(id).orElseThrow(() -> ApiError.notFound("Role not found with id=" + id));
    }

    @Override
    public Role getByName(String name) {
        Role role = getByNameOrNull(name);
        if (role == null) {
            ApiError.notFound("Role not found with name=" + name);
        }
        return role;
    }

    @Override
    public Role getByNameOrNull(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role save(RoleDto roleDto) {
        Role savedRole = getByNameOrNull(roleDto.getName());
        if (savedRole != null) {
            ApiError.conflict("Role already exists with this name");
        }
        Role role = new Role(roleDto.getName());
        return save(role);
    }
}
