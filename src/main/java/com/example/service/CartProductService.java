package com.example.service;

import com.example.dto.CartProductDto;
import com.example.dto.UpdateCartProductDto;
import com.example.entity.CartProduct;
import com.example.entity.User;
import com.example.util.CartProductId;

import java.util.List;

public interface CartProductService {
    List<CartProduct> getAll();

    List<CartProduct> getAllByCartId(long cartId);

    List<CartProduct> getAllByCustomer(User authenticatedCustomer);

    CartProduct getById(CartProductId cartProductId);

    CartProduct getByIdOrNull(CartProductId cartProductId);

    CartProduct save(CartProduct cartProduct);

    CartProduct save(User authenticatedCustomer, CartProductDto cartProductDto);

    CartProduct update(User authenticatedCustomer, long sellerId, long productId, UpdateCartProductDto updateCartProductDto);

    void delete(User authenticatedCustomer, long sellerId, long productId);

    void deleteAllByCartId(long cartId);
}
