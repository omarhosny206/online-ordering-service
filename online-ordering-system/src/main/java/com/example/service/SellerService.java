package com.example.service;

import com.example.model.Customer;
import com.example.model.Order;
import com.example.model.Seller;
import com.example.model.SellerProduct;
import com.example.repository.OrderRepository;
import com.example.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository, OrderRepository orderRepository) {
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
    }

    public List<Seller> getAll() {
        return sellerRepository.findAll();
    }

    public Seller getById(int id) {
        return sellerRepository.findById(id).orElse(null);
    }

    public void save(Seller seller) {
        sellerRepository.save(seller);
    }

    @Transactional
    public void update(int id, Seller seller) {
        Seller sellerToUpdate = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + id + "does not exist"));
        sellerToUpdate.clone(seller);
    }

    public void deleteById(int id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if(seller.isEmpty())
            throw new IllegalArgumentException("Seller with id " + id + " does not exist");
        seller.get().getOrders().forEach(order -> order.setSeller(null));
        sellerRepository.deleteById(id);
    }

    public Set<Customer> getCustomers(int id) {
        Set<Customer> customers = new HashSet<>();
        List<Order> orders = getOrders(id);
        for(Order order: orders)
            customers.add(order.getCustomer());
        return customers;
    }

    public List<Order> getOrders(int id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if(seller == null)
            return new ArrayList<>();
        return seller.getOrders();
    }

    public List<SellerProduct> getProducts(int id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if(seller == null)
            return new ArrayList<>();
        return seller.getSellerProducts();
    }
}
