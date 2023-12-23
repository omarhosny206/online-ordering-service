package com.example.controller;

import com.example.entity.Cart;
import com.example.response.MessageResponse;
import com.example.service.CartService;
import com.example.util.AuthenticationUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@PreAuthorize("hasAuthority('customer')")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public ResponseEntity<Cart> getByCustomerId(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.getByCustomerId(AuthenticationUser.get(authentication).getId()));
    }

    @DeleteMapping("/")
    public ResponseEntity<MessageResponse> clear(Authentication authentication) {
        cartService.clear(AuthenticationUser.get(authentication));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Cart is cleared successfully"));
    }
}
