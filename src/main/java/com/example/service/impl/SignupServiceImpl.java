package com.example.service.impl;

import com.example.dto.SignupRequestDto;
import com.example.dto.TribeRequestDto;
import com.example.entity.User;
import com.example.util.ApiError;
import com.example.service.SignupService;
import com.example.service.TribeService;
import com.example.service.UserService;
import com.example.util.UsernameGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {
    private final UserService userService;
    private final TribeService tribeService;
    private final PasswordEncoder passwordEncoder;

    public SignupServiceImpl(UserService userService, TribeService tribeService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tribeService = tribeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signup(SignupRequestDto signupRequestDto) {
        User userByEmail = userService.getByEmailOrNull(signupRequestDto.getEmail());

        if (userByEmail != null) {
            throw ApiError.badRequest("User with this email already exists, choose another email");
        }

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
                passwordEncoder.encode(signupRequestDto.getPassword())
        );

        User savedUser = userService.save(user);
        tribeService.save(savedUser, new TribeRequestDto("own.tribe." + savedUser.getId()));
        return savedUser;
    }
}
