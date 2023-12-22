package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${access.token.secret.key}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${refresh.token.secret.key}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${access.token.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${refresh.token.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, ACCESS_TOKEN_SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, REFRESH_TOKEN_SECRET_KEY)
                .compact();
    }

    public Claims verifyAccessToken(String accessToken) {
        Claims claims = Jwts.parser().setSigningKey(ACCESS_TOKEN_SECRET_KEY).parseClaimsJws(accessToken).getBody();
        return claims;
    }

    public Claims verifyRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser().setSigningKey(REFRESH_TOKEN_SECRET_KEY).parseClaimsJws(refreshToken).getBody();
        return claims;
    }

    public String getSubject(Claims claims) {
        return claims.getSubject();
    }
}