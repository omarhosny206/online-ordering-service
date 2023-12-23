package com.example.service;

import com.example.dto.RoleDto;
import com.example.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    Role getById(long id);

    Role getByName(String name);

    Role getByNameOrNull(String name);

    Role save(Role role);

    Role save(RoleDto roleDto);
}
