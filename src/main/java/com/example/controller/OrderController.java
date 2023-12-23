package com.example.controller;

import com.example.entity.Order;
import com.example.response.MessageResponse;
import com.example.service.OrderService;
import com.example.util.AuthenticationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("hasAuthority('customer')")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAll());
    }

    @GetMapping("/profile")
    public ResponseEntity<List<Order>> getAllByCustomerId(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getByIdCustomerId(AuthenticationUser.get(authentication).getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Order> save(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.save(AuthenticationUser.get(authentication)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(Authentication authentication, @PathVariable int id) {
        orderService.deleteById(AuthenticationUser.get(authentication), id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Deleted successfully"));
    }
}
