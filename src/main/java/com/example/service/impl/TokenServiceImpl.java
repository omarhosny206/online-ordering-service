package com.example.service.impl;

import com.example.dto.TokenRequestDto;
import com.example.dto.TokenResponseDto;
import com.example.service.TokenService;
import com.example.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final JwtUtil jwtUtil;

    public TokenServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public TokenResponseDto regenerateTokens(TokenRequestDto tokenRequestDto) {
        Claims claims = jwtUtil.verifyRefreshToken(tokenRequestDto.getRefreshToken());
        String email = jwtUtil.getSubject(claims);

        TokenResponseDto tokenResponseDto = new TokenResponseDto(jwtUtil.generateAccessToken(email),
                jwtUtil.generateRefreshToken(email));
        return tokenResponseDto;
    }
}
