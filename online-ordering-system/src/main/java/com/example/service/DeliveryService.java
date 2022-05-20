package com.example.service;

import com.example.model.*;
import com.example.repository.DeliveryRepository;
import com.example.repository.SellerProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {
    private DeliveryRepository deliveryRepository;
    private SellerProductRepository sellerProductRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, SellerProductRepository sellerProductRepository) {
        this.deliveryRepository = deliveryRepository;
        this.sellerProductRepository = sellerProductRepository;
    }

    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }

    public Delivery getById(int id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        deliveryRepository.deleteById(id);
    }

    public Customer getCustomer(int id) {
        Delivery delivery = getById(id);

        if (delivery == null)
            return null;

        return delivery.getCustomer();
    }

    public List<Order> getOrders(int id) {
        Delivery delivery = getById(id);

        if (delivery == null)
            return new ArrayList<>();

        return delivery.getOrders();
    }

    public List<Product> getProducts(int id) {
        List<Product> products = new ArrayList<>();
        List<Order> orders = getOrders(id);

        if (orders.size() == 0)
            return new ArrayList<>();

        orders.forEach(order -> products.add(order.getProduct()));
        return products;
    }

    public List<Seller> getSellers(int id) {
        List<Seller> sellers = new ArrayList<>();
        List<Order> orders = getOrders(id);

        if (orders.size() == 0)
            return new ArrayList<>();

        orders.forEach(order -> sellers.add(order.getSeller()));
        return sellers;
    }

    public Payment getPayment(int id) {
        Delivery delivery = getById(id);

        if (delivery == null)
            return null;

        return delivery.getPayment();
    }

    public int getTotalPrice(int id) {
        int totalPrice = 0;
        List<Product> products = getProducts(id);
        List<Seller> sellers = getSellers(id);

        if (products.size() == 0)
            return 0;

        for (int i = 0; i < products.size(); ++i) {
            SellerProduct sellerProduct = sellerProductRepository.findById(new SellerProductId(sellers.get(i).getId(), products.get(i).getId())).orElse(null);
            totalPrice += (sellerProduct == null) ? 0 : sellerProduct.getPrice();
        }

        return totalPrice;
    }

    @Transactional
    public Payment addPayment(int id) {
        Payment payment = new Payment();
        Delivery delivery = getById(id);

        if (delivery == null)
            return null;

        payment.setDelivery(delivery);
        delivery.setPayment(payment);
        return payment;
    }
}
