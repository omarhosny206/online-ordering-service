package com.example.service;

import com.example.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();

    User getById(long id);

    User getByEmail(String email);

    User getByEmailOrNull(String email);

    User getByUsername(String username);

    User getByUsernameOrNull(String username);

    User save(User user);
}
