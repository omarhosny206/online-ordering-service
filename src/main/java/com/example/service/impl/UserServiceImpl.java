package com.example.service.impl;

import com.example.dto.UpdateUserDto;
import com.example.dto.UpdateUserEmailDto;
import com.example.dto.UpdateUserUsernameDto;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.ApiError;
import com.example.util.CustomUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> ApiError.notFound("User not found with id=" + id));
    }

    @Override
    public User getByEmail(String email) {
        User user = getByEmailOrNull(email);
        if (user == null) {
            throw ApiError.notFound("User not found with email=" + email);
        }
        return user;
    }

    @Override
    public User getByEmailOrNull(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByUsername(String username) {
        User user = getByUsernameOrNull(username);
        if (user == null) {
            throw ApiError.notFound("User not found with username=" + username);
        }
        return user;
    }

    @Override
    public User getByUsernameOrNull(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User authenticatedUser, UpdateUserDto updateUserDto) {
        authenticatedUser.setFirstName(updateUserDto.getFirstName());
        authenticatedUser.setLastName(updateUserDto.getLastName());
        return save(authenticatedUser);
    }

    @Override
    public User updateEmail(User authenticatedUser, UpdateUserEmailDto updateUserEmailDto) {
        boolean existsByEmail = getByEmailOrNull(updateUserEmailDto.getEmail()) != null;
        if (existsByEmail) {
            throw ApiError.conflict("Email is already used, choose another one");
        }
        authenticatedUser.setEmail(updateUserEmailDto.getEmail());
        return save(authenticatedUser);
    }

    @Override
    public User updateUsername(User authenticatedUser, UpdateUserUsernameDto updateUserUsernameDto) {
        boolean existsByUsername = getByUsernameOrNull(updateUserUsernameDto.getUsername()) != null;
        if (existsByUsername) {
            throw ApiError.conflict("Username is already used, choose another one");
        }
        authenticatedUser.setUsername(updateUserUsernameDto.getUsername());
        return save(authenticatedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmailOrNull(email);
        if (user == null) {
            throw ApiError.unauthorized("Bad credentials");
        }
        return new CustomUser(user);
    }
}
