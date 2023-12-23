package com.example.controller;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.response.MessageResponse;
import com.example.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasAnyAuthority('admin', 'seller')")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Product> save(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.save(productDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Product> update(@PathVariable long id, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.update(id, productDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable int id) {
        productService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Deleted successfully"));
    }
}
