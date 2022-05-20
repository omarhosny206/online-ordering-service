package com.example.controller;

import com.example.model.Customer;
import com.example.model.Payment;
import com.example.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable int id) {
        return paymentService.getById(id);
    }

    @GetMapping("/{id}/customer")
    public Customer getCustomer(@PathVariable int id) {
     return paymentService.getCustomer(id);
    }

    @GetMapping("/{id}/total-price")
    public double getTotalPrice(int id) {
        return paymentService.getTotalPrice(id);
    }
}
