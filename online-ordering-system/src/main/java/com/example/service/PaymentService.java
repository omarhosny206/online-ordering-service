package com.example.service;

import com.example.model.Customer;
import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
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


}
