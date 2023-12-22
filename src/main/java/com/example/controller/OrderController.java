package com.example.controller;

import com.example.entity.Delivery;
import com.example.entity.Order;
import com.example.entity.Product;
import com.example.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") int id) {
        return orderService.getById(id);
    }

    @GetMapping("/{id}/customers")
    public Customer getCustomer(@PathVariable("id") int id) {
        return orderService.getCustomer(id);
    }

    @GetMapping("/{id}/sellers")
    public Seller getSeller(@PathVariable("id") int id) {
        return orderService.getSeller(id);
    }

    @GetMapping("/{id}/products")
    public Product getProduct(@PathVariable("id") int id) {
        return orderService.getProduct(id);
    }

    @GetMapping("/{id}/deliveries")
    public Delivery getDelivery(@PathVariable("id") int id) {
        return orderService.getDelivery(id);
    }

    @PostMapping("/")
    public void save(
            @RequestBody Order order,
            @RequestParam("customerId") int customerId,
            @RequestParam("sellerId") int sellerId,
            @RequestParam("productId") int productId,
            @RequestParam("deliveryId") int deliveryId
    ) {
        orderService.save(order, customerId, sellerId, productId, deliveryId);
    }


    @PutMapping("/{id}")
    public void update(
            @PathVariable("id") Integer orderId,
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "sellerId", required = false) Integer sellerId,
            @RequestParam(name = "productId", required = false) Integer productId,
            @RequestParam(name = "deliveryId", required = false) Integer deliveryId
    ) {
        orderService.update(orderId, customerId, sellerId, productId, deliveryId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        orderService.deleteById(id);
    }

}
