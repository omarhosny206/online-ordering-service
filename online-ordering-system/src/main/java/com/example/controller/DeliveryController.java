package com.example.controller;

import com.example.model.*;
import com.example.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        deliveryService.deleteById(id);
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

    @GetMapping("/{id}/sellers")
    public List<Seller> getAllSellers(@PathVariable int id) {
        return deliveryService.getAllSellers(id);
    }

    @GetMapping("/{id}/total-price")
    public int getTotalPrice(@PathVariable int id) {
        return deliveryService.getTotalPrice(id);
    }
}
