package com.example.controller;

import com.example.entity.UserProduct;
import com.example.service.impl.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellersProducts")
public class SellerProductController {

    private SellerProductService sellerProductService;

    @Autowired
    public SellerProductController(SellerProductService sellerProductService) {
        this.sellerProductService = sellerProductService;
    }

    @GetMapping("/")
    public List<UserProduct> getAll() {
        return sellerProductService.getAll();
    }

    @GetMapping("/{sellerId}/{productId}")
    public UserProduct getById(@PathVariable("sellerId") int sellerId, @PathVariable("productId") int productId) {
        return sellerProductService.getById(sellerId, productId);
    }

    @PostMapping("/")
    public void save(@RequestBody UserProduct userProduct) {
        sellerProductService.save(userProduct);
    }

    @PutMapping("/{sellerId}/{productId}")
    public void update(
            @PathVariable("sellerId") int sellerId,
            @PathVariable("productId") int productId,
            @RequestBody UserProduct userProduct
    ) {
        sellerProductService.update(sellerId, productId, userProduct);
    }

    @DeleteMapping("/{sellerId}/{productId}")
    public void delete(@PathVariable("sellerId") int sellerId, @PathVariable("productId") int productId) {
        sellerProductService.delete(sellerId, productId);
    }
}
