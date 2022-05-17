package com.example.service;

import com.example.model.Customer;
import com.example.model.Delivery;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {
    private DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }

    public Delivery getById(int id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public Customer getCustomer(int id) {
        Delivery delivery = getById(id);

        if (delivery == null)
            return null;

        return delivery.getCustomer();
    }

    public List<Order> getAllOrders(int id) {
        Delivery delivery = getById(id);

        if (delivery == null)
            return new ArrayList<>();

        return delivery.getOrders();
    }

    public List<Product> getAllProducts(int id) {
        List<Product> products = new ArrayList<>();
        List<Order> orders = getAllOrders(id);
        
        if(orders.size() == 0)
            return new ArrayList<>();

        orders.forEach(order -> products.add(order.getProduct()));
        return products;
    }
}
