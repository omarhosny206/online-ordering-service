package com.example.service;

import com.example.entity.CartProduct;
import com.example.entity.Order;
import com.example.entity.SellerProduct;
import com.example.entity.User;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    List<Order> getByIdCustomerId(long customerId);

    Order getById(long id);

    Order save(Order order);

    Order save(User authenticatedCustomer);

    void deleteById(User authenticatedCustomer, long id);

    double getTotalPrice(List<CartProduct> cartProducts, List<SellerProduct> sellerProducts);

    void checkAuthority(User authenticatedCustomer, Order order);
}
