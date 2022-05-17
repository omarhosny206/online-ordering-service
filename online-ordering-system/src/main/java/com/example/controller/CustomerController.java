package com.example.controller;

import com.example.model.Customer;
import com.example.model.Delivery;
import com.example.model.Order;
import com.example.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        return customerService.getById(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable int id) {
        return customerService.getAllOrders(id);
    }

    @GetMapping("/{id}/deliveries")
    public List<Delivery> getAllDeliveries(@PathVariable int id) {
        return customerService.getAllDeliveries(id);
    }
}
