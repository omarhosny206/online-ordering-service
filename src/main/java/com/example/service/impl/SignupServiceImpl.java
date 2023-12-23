package com.example.service.impl;

import com.example.dto.SignupRequestDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.CartService;
import com.example.service.RoleService;
import com.example.service.SignupService;
import com.example.service.UserService;
import com.example.util.ApiError;
import com.example.util.Roles;
import com.example.util.UsernameGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {
    private final UserService userService;
    private final RoleService roleService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    public SignupServiceImpl(UserService userService, RoleService roleService, CartService cartService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signup(SignupRequestDto signupRequestDto) {
        User userByEmail = userService.getByEmailOrNull(signupRequestDto.getEmail());

        if (userByEmail != null) {
            throw ApiError.conflict("User with this email already exists, choose another email");
        }

        Role role = roleService.getByName(signupRequestDto.getRole());

        String generatedUsername = null;

        while (true) {
            generatedUsername = UsernameGenerator.generateFromEmail(signupRequestDto.getEmail());
            User userWithUsername = userService.getByUsernameOrNull(generatedUsername);
            if (userWithUsername == null) {
                break;
            }
        }

        User user = new User(
                signupRequestDto.getFirstName(),
                signupRequestDto.getLastName(),
                generatedUsername,
                signupRequestDto.getEmail(),
                passwordEncoder.encode(signupRequestDto.getPassword()),
                role
        );

        User savedUser = userService.save(user);
        if (savedUser.getRole().getName().equals(Roles.CUSTOMER.getValue())) {
            cartService.save(savedUser);
        }
        return savedUser;
    }
}
