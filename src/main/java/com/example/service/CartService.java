package com.example.service;

import com.example.entity.Cart;
import com.example.entity.User;

public interface CartService {
    Cart getById(long id);

    Cart getByCustomerId(long customerId);

    Cart save(User user);

    void clear(User authenticatedCustomer);

    void clear(User authenticatedCustomer, Cart cart);

    void checkAuthority(User authenticatedCustomer, Cart cart);
}
