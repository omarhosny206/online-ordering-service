package com.example.util;

import com.example.entity.User;
import org.springframework.security.core.Authentication;

public class AuthenticationUser {
    private static Authentication authentication;

    public static User get(Authentication authentication) {
        return ((CustomUser) authentication.getPrincipal()).getUser();
    }
}
