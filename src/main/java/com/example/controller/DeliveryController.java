package com.example.controller;

import com.example.dto.DeliveryDto;
import com.example.entity.Delivery;
import com.example.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@PreAuthorize("hasAuthority('customer')")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Delivery>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deliveryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deliveryService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Delivery> save(Authentication authentication, @Valid @RequestBody DeliveryDto deliveryDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deliveryService.save(deliveryDto));
    }
}
