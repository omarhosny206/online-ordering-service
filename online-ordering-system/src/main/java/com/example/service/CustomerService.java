package com.example.service;

import com.example.model.Customer;
import com.example.model.Delivery;
import com.example.model.Order;
import com.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders(int id) {
        Customer customer = getById(id);

        if (customer == null)
            return new ArrayList<>();

        return customer.getOrders();
    }

    public List<Delivery> getAllDeliveries(int id) {
        Customer customer = getById(id);

        if (customer == null)
            return new ArrayList<>();

        return customer.getDeliveries();
    }
}
