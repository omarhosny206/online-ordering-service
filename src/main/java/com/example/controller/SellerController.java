package com.example.controller;

import com.example.entity.Order;
import com.example.entity.UserProduct;
import com.example.service.impl.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/sellers")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/")
    public List<Seller> getAll() {
        return sellerService.getAll();
    }

    @GetMapping("/{id}")
    public Seller getById(@PathVariable("id") int id) {
        return sellerService.getById(id);
    }

    @PostMapping("/")
    public void save(@RequestBody Seller seller) {
        sellerService.save(seller);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody Seller seller) {
        sellerService.update(id, seller);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        sellerService.deleteById(id);
    }

    @GetMapping("/{id}/customers")
    public Set<Customer> getCustomers(@PathVariable("id") int id) {
        return sellerService.getCustomers(id);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getOrders(@PathVariable("id") int id) {
        return sellerService.getOrders(id);
    }

    @GetMapping("/{id}/products")
    public List<UserProduct> getProducts(@PathVariable("id") int id) {
        return sellerService.getProducts(id);
    }
}
