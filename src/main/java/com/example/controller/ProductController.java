package com.example.controller;

import com.example.entity.*;
import com.example.service.impl.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping("/")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/")
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        productService.deleteById(id);
    }

    @GetMapping("/{id}/category")
    public Category getCategory(@PathVariable int id) {
        return productService.getCategory(id);
    }

    @GetMapping("/{id}/sellers")
    public List<Seller> getSellers(@PathVariable int id) {
        return productService.getSellers(id);
    }

    @GetMapping("/{id}/prices")
    public List<Double> getPrices(@PathVariable int id) {
        return productService.getPrices(id);
    }

    @GetMapping("/{id}/sellers-prices")
    public List<UserProduct> getSellersPrices(@PathVariable int id) {
        return productService.getSellersPrices(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getOrders(@PathVariable int id) {
        return productService.getOrders(id);
    }

    @GetMapping("/{id}/deliveries")
    public List<Delivery> getDeliveries(@PathVariable int id) {
        return productService.getDeliveries(id);
    }

    @GetMapping("/{id}/customers")
    public List<Customer> getCustomers(@PathVariable int id) {
        return productService.getCustomers(id);
    }

    @PostMapping("/{id}/{categoryId}")
    @Transactional
    public Category addCategory(@PathVariable int id, @PathVariable int categoryId) {
        return productService.addCategory(id, categoryId);
    }

    @PostMapping("/{id}/order")
    @Transactional
    public Order addOrder(@PathVariable int id) {
        return productService.addOrder(id);
    }
}
