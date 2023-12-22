package com.example.service;

import com.example.dto.SignupRequestDto;
import com.example.entity.User;

public interface SignupService {
    User signup(SignupRequestDto signupRequestDto);
}
