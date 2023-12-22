package com.example.service;

import com.example.dto.LoginRequestDto;
import com.example.dto.LoginResponseDto;

public interface LoginService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
