package com.example.service.impl;

import com.example.entity.CartProduct;
import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.SellerProduct;
import com.example.repository.OrderProductRepository;
import com.example.service.OrderProductService;
import com.example.util.OrderProductId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<OrderProduct> getAll() {
        return orderProductRepository.findAll();
    }

    @Override
    public List<OrderProduct> getAllByOrderId(long orderId) {
        return orderProductRepository.findAllByOrderId(orderId);
    }

    @Override
    public List<OrderProduct> saveAll(List<OrderProduct> orderProducts) {
        return orderProductRepository.saveAll(orderProducts);
    }

    @Override
    public List<OrderProduct> saveAll(Order order, List<CartProduct> cartProducts, List<SellerProduct> sellerProducts) {
        List<OrderProduct> orderProducts = sellerProducts.stream().map(sellerProduct -> {
            OrderProductId orderProductId = new OrderProductId(order.getId(), sellerProduct.getSellerProductId().getSellerId(), sellerProduct.getSellerProductId().getProductId());
            return new OrderProduct(orderProductId, order, sellerProduct.getSeller(), sellerProduct.getProduct(), sellerProduct.getPrice());
        }).toList();

        for (int i = 0; i < cartProducts.size(); ++i)
            orderProducts.get(i).setQuantity(cartProducts.get(i).getQuantity());

        return saveAll(orderProducts);
    }

    @Override
    public void deleteAllByOrderId(long orderId) {
        orderProductRepository.deleteAllByOrderId(orderId);
    }
}
