package com.example.service;

import com.example.model.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerService, SellerRepository sellerRepository, ProductRepository productRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerService;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Customer getCustomer(int id) {
        Order order = getById(id);
        if (order != null)
            return order.getCustomer();
        return null;
    }

    public Seller getSeller(int id) {
        Order order = getById(id);
        if (order != null)
            return order.getSeller();
        return null;
    }

    public Product getProduct(int id) {
        Order order = getById(id);
        if (order != null)
            return order.getProduct();
        return null;
    }

    public Delivery getDelivery(int id) {
        Order order = getById(id);
        if (order != null)
            return order.getDelivery();
        return null;
    }

    public void save(Order order, Integer customerId, Integer sellerId, Integer productId, Integer deliveryId) {
        order.setCustomer(customerRepository.findById(customerId).orElse(null));
        order.setSeller(sellerRepository.findById(sellerId).orElse(null));
        order.setProduct(productRepository.findById(productId).orElse(null));
        order.setDelivery(deliveryRepository.findById(deliveryId).orElse(null));
        orderRepository.save(order);
    }

    @Transactional
    public void update(Integer orderId, Integer customerId, Integer sellerId, Integer productId, Integer deliveryId) {
        Order orderToUpdate = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + "does not exist"));

        orderToUpdate.setCustomer(
                customerId != null ? customerRepository.findById(customerId).orElse(null) : orderToUpdate.getCustomer()
        );
        orderToUpdate.setSeller(
                sellerId != null ? sellerRepository.findById(sellerId).orElse(null) : orderToUpdate.getSeller()
        );
        orderToUpdate.setProduct(
                productId != null ? productRepository.findById(productId).orElse(null) : orderToUpdate.getProduct()
        );
        orderToUpdate.setDelivery(
                deliveryId != null ? deliveryRepository.findById(deliveryId).orElse(null) : orderToUpdate.getDelivery()
        );
    }

    public void deleteById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new IllegalArgumentException("Order with id " + id + " does not exist");
        orderRepository.deleteById(id);
    }
}
