package com.example.controller;

import com.example.model.Customer;
import com.example.model.Delivery;
import com.example.model.Order;
import com.example.model.Product;
import com.example.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @PostMapping("/")
    public Customer save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/")
    public Customer update(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        customerService.deleteById(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getOrders(@PathVariable int id) {
        return customerService.getOrders(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable int id) {
        return customerService.getProducts(id);
    }

    @GetMapping("/{id}/deliveries")
    public List<Delivery> getDeliveries(@PathVariable int id) {
        return customerService.getDeliveries(id);
    }

    @PostMapping("/{id}/order")
    @Transactional
    public Order addOrder(@PathVariable int id) {
        return customerService.addOrder(id);
    }

    @PostMapping("/{id}/delivery")
    @Transactional
    public void addDelivery(@PathVariable int id) {
        customerService.addDelivery(id);
    }
}
