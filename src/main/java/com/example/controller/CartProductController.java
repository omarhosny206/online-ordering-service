package com.example.controller;

import com.example.dto.CartProductDto;
import com.example.dto.UpdateCartProductDto;
import com.example.entity.CartProduct;
import com.example.response.MessageResponse;
import com.example.service.CartProductService;
import com.example.util.AuthenticationUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts-products")
@PreAuthorize("hasAuthority('customer')")
public class CartProductController {
    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CartProduct>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartProductService.getAll());
    }

    @GetMapping("/profile")
    public ResponseEntity<List<CartProduct>> getAllByCustomerCartId(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartProductService.getAllByCustomer(AuthenticationUser.get(authentication)));
    }


    @PostMapping("/")
    public ResponseEntity<CartProduct> save(Authentication authentication, @Valid @RequestBody CartProductDto cartProductDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartProductService.save(AuthenticationUser.get(authentication), cartProductDto));
    }

    @PutMapping("/{sellerId}/{productId}")
    public ResponseEntity<CartProduct> update(Authentication authentication, @PathVariable long sellerId, @PathVariable long productId, @Valid @RequestBody UpdateCartProductDto updateCartProductDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartProductService.update(AuthenticationUser.get(authentication), sellerId, productId, updateCartProductDto));
    }

    @DeleteMapping("/{sellerId}/{productId}")
    public ResponseEntity<MessageResponse> deleteById(Authentication authentication, @PathVariable long sellerId, @PathVariable long productId) {
        cartProductService.delete(AuthenticationUser.get(authentication), sellerId, productId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Deleted successfully"));
    }
}
