package com.example.controller;

import com.example.model.*;
import com.example.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
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
    public List<Order> getOrders(@PathVariable int id) {
        return deliveryService.getOrders(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable int id) {
        return deliveryService.getProducts(id);
    }

    @GetMapping("/{id}/sellers")
    public List<Seller> getSellers(@PathVariable int id) {
        return deliveryService.getSellers(id);
    }

    @GetMapping("/{id}/total-price")
    public int getTotalPrice(@PathVariable int id) {
        return deliveryService.getTotalPrice(id);
    }

    @PostMapping("/{id}/payment")
    @Transactional
    public Payment addPayment(@PathVariable int id) {
        return deliveryService.addPayment(id);
    }
}
