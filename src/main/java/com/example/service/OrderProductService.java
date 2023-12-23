package com.example.service;

import com.example.entity.CartProduct;
import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.SellerProduct;

import java.util.List;

public interface OrderProductService {
    List<OrderProduct> getAll();

    List<OrderProduct> getAllByOrderId(long orderId);

    List<OrderProduct> saveAll(List<OrderProduct> orderProducts);

    List<OrderProduct> saveAll(Order order, List<CartProduct> cartProducts, List<SellerProduct> sellerProducts);

    void deleteAllByOrderId(long orderId);
}
