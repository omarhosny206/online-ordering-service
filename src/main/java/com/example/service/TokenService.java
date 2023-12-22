package com.example.service;

import com.example.dto.TokenRequestDto;
import com.example.dto.TokenResponseDto;

public interface TokenService {
    TokenResponseDto regenerateTokens(TokenRequestDto tokenRequestDto);
}
