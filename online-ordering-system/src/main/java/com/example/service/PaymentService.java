package com.example.service;

import com.example.model.Customer;
import com.example.model.Delivery;
import com.example.model.Payment;
import com.example.repository.PaymentRepository;
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
        return paymentRepository.getById(id);
    }

    public Customer getCustomer(int id) {
        Payment payment = getById(id);

        if (payment == null)
            return null;

        return payment.getDelivery().getCustomer();
    }

    public int getTotalPrice(int id) {
        Payment payment = getById(id);
        Delivery delivery = null;

        if (payment == null)
            return 0;

        delivery = payment.getDelivery();
        return deliveryService.getTotalPrice(delivery.getId());
    }
}