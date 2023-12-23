package com.example.controller;

import com.example.dto.UpdateUserDto;
import com.example.entity.User;
import com.example.service.CartProductService;
import com.example.service.UserService;
import com.example.util.AuthenticationUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final CartProductService cartProductService;

    public UserController(UserService userService, CartProductService cartProductService) {
        this.userService = userService;
        this.cartProductService = cartProductService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getById(AuthenticationUser.get(authentication).getId()));
    }

    @PutMapping("/")
    public ResponseEntity<User> update(Authentication authentication, @Valid @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(AuthenticationUser.get(authentication), updateUserDto));
    }
}
