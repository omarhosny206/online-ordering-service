package com.example.controller;

import com.example.model.Customer;
import com.example.model.Delivery;
import com.example.model.Order;
import com.example.model.Product;
import com.example.service.DeliveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/delveries")
public class DeliveryController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/")
    public List<Delivery> getAll() {
        return deliveryService.getAll();
    }

    @GetMapping("/{id}")
    public Delivery getById(@PathVariable int id) {
        return deliveryService.getById(id);
    }

    @GetMapping("/{id}/customer")
    public Customer getCustomer(@PathVariable int id) {
        return deliveryService.getCustomer(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable int id) {
        return deliveryService.getAllOrders(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getAllProducts(@PathVariable int id) {
       return deliveryService.getAllProducts(id);
    }
}
