package com.example.controller;

import com.example.model.SellerProduct;
import com.example.service.SellerProductService;
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
    public List<SellerProduct> getAll() {
        return sellerProductService.getAll();
    }

    @GetMapping("/{sellerId}/{productId}")
    public SellerProduct getById(@PathVariable("sellerId") int sellerId, @PathVariable("productId") int productId) {
        return sellerProductService.getById(sellerId, productId);
    }

    @PostMapping("/")
    public void save(@RequestBody SellerProduct sellerProduct) {
        sellerProductService.save(sellerProduct);
    }

    @PutMapping("/{sellerId}/{productId}")
    public void update(
            @PathVariable("sellerId") int sellerId,
            @PathVariable("productId") int productId,
            @RequestBody SellerProduct sellerProduct
    ) {
        sellerProductService.update(sellerId, productId, sellerProduct);
    }

    @DeleteMapping("/{sellerId}/{productId}")
    public void delete(@PathVariable("sellerId") int sellerId, @PathVariable("productId") int productId) {
        sellerProductService.delete(sellerId, productId);
    }
}
