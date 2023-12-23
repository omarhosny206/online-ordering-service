package com.example.service;

import com.example.dto.UpdateUserDto;
import com.example.dto.UpdateUserEmailDto;
import com.example.dto.UpdateUserUsernameDto;
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

    User update(User authenticatedUser, UpdateUserDto updateUserDto);

    User updateEmail(User authenticatedUser, UpdateUserEmailDto updateUserEmailDto);

    User updateUsername(User authenticatedUser, UpdateUserUsernameDto updateUserUsernameDto);
}
