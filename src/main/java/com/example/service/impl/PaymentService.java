package com.example.service.impl;

import com.example.entity.Delivery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;
    private DeliveryService deliveryService;

    public PaymentService(PaymentRepository paymentRepository, DeliveryService deliveryService) {
        this.paymentRepository = paymentRepository;
        this.deliveryService = deliveryService;
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getById(int id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Customer getCustomer(int id) {
        Payment payment = getById(id);

        if (payment == null)
            return null;

        return payment.getDelivery().getCustomer();
    }

    public double getTotalPrice(int id) {
        Payment payment = getById(id);
        Delivery delivery = null;

        if (payment == null)
            return 0;

        delivery = payment.getDelivery();
        return deliveryService.getTotalPrice(delivery.getId());
    }
}
