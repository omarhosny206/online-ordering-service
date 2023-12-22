package com.example.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.dto.ErrorDto;
import com.example.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    private final String[] WHITE_LIST_ENDPOINTS = new String[]{
            "/api/login/**",
            "/api/signup/**",
            "/api/tokens/**",
            "/api/roles/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Starting JWT authentication filter");

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorizationHeader.substring(7);
        Claims claims = null;
        UserDetails user = null;

        try {
            claims = jwtUtil.verifyAccessToken(accessToken);
            String email = jwtUtil.getSubject(claims);
            user = userDetailsService.loadUserByUsername(email);
        } catch (Exception ex) {
            sendAuthenticationErrorResponse(request, response, ex);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            log.info("Authentication is successful");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRequestURI = request.getRequestURI();
        log.info("Starting shouldNotFilter for url=" + currentRequestURI);
        boolean shouldNotBeFiltered = Arrays.stream(WHITE_LIST_ENDPOINTS).anyMatch(endpoint -> endpoint.contains(currentRequestURI));
        log.info("Should be filtered? " + (shouldNotBeFiltered ? "No" : "Yes"));
        return shouldNotBeFiltered;
    }

    private void sendAuthenticationErrorResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception ex
    ) throws IOException {
        log.error("Authentication failed, invalid JWT");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), new ErrorDto("Unauthorized (invalid JWT): " + ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }
}