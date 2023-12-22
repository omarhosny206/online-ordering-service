package com.example.service.impl;

import com.example.entity.Delivery;
import com.example.entity.Order;
import com.example.entity.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private DeliveryService deliveryService;

    public CustomerService(CustomerRepository customerRepository, DeliveryService deliveryService) {
        this.customerRepository = customerRepository;
        this.deliveryService = deliveryService;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllByFullName(String firstName, String lastName) {
        List<Customer> customers = customerRepository.findAllByFirstNameAndLastNameIgnoreCase(firstName, lastName);

        if (customers == null)
            return new ArrayList<>();

        return customers;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }


    public List<Order> getOrders(int id) {
        Customer customer = getById(id);

        if (customer == null)
            return new ArrayList<>();

        return customer.getOrders();
    }

    public List<Product> getProducts(int id) {
        List<Product> products = new ArrayList<>();
        List<Order> orders = getOrders(id);

        if (orders == null)
            return new ArrayList<>();

        orders.stream().forEach(order -> products.add(order.getProduct()));
        return products;
    }

    public List<Delivery> getDeliveries(int id) {
        Customer customer = getById(id);

        if (customer == null)
            return new ArrayList<>();

        return customer.getDeliveries();
    }

    public List<Payment> getPayments(int id) {
        List<Payment> payments = new ArrayList<>();
        Customer customer = getById(id);
        List<Delivery> deliveries = new ArrayList<>();

        if (customer == null)
            return new ArrayList<>();

        deliveries = customer.getDeliveries();

        deliveries.stream().forEach(delivery -> {
            Payment payment = delivery.getPayment();
            if (payment != null) {
                payments.add(payment);
            }

        });

        return payments;
    }

    @Transactional
    public Delivery addDelivery(int id) {
        Delivery delivery = new Delivery();
        Customer customer = getById(id);

        if (customer == null)
            return null;

        customer.getDeliveries().add(delivery);
        delivery.setCustomer(customer);
        return delivery;
    }
}
