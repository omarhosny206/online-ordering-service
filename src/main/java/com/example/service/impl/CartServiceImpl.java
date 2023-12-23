package com.example.service.impl;

import com.example.entity.Cart;
import com.example.entity.User;
import com.example.repository.CartRepository;
import com.example.service.CartProductService;
import com.example.service.CartService;
import com.example.util.ApiError;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductService cartProductService;

    public CartServiceImpl(CartRepository cartRepository, @Lazy CartProductService cartProductService) {
        this.cartRepository = cartRepository;
        this.cartProductService = cartProductService;
    }

    @Override
    public Cart getById(long id) {
        return cartRepository.findById(id).orElseThrow(() -> ApiError.notFound("Cart not found with id=" + id));
    }

    @Override
    public Cart getByCustomerId(long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            throw ApiError.notFound("Cart not found with customerId=" + customerId);
        }
        return cart;
    }

    @Override
    public Cart save(User user) {
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart != null) {
            throw ApiError.conflict("Cart already exists for this user");
        }
        Cart cartToSave = new Cart(user);
        return cartRepository.save(cartToSave);
    }

    @Override
    public void clear(User authenticatedCustomer) {
        Cart cart = getByCustomerId(authenticatedCustomer.getId());
        checkAuthority(authenticatedCustomer, cart);
        cartProductService.deleteAllByCartId(cart.getId());
    }

    @Override
    public void clear(User authenticatedCustomer, Cart cart) {
        checkAuthority(authenticatedCustomer, cart);
        cartProductService.deleteAllByCartId(cart.getId());
    }

    @Override
    public void checkAuthority(User authenticatedCustomer, Cart cart) {
        if (authenticatedCustomer.getId() != cart.getCustomer().getId()) {
            throw ApiError.forbidden("Cannot do this action, you are not the author.");
        }
    }
}
