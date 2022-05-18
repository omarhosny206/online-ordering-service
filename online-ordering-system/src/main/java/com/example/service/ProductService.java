package com.example.service;

import com.example.model.*;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import com.example.repository.SellerProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private SellerProductRepository sellerProductRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SellerProductRepository sellerProductRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sellerProductRepository = sellerProductRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        Product old = getById(product.getId());

        if(old == null)
            return null;

        return productRepository.save(old.clone(product));
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Category getCategory(int id) {
        Product product = getById(id);

        if (product == null)
            return null;

        return product.getCategory();
    }

    public List<Seller> getSellers(int id) {
        List<Seller> sellers = new ArrayList<>();
        List<SellerProduct> sellerProducts = sellerProductRepository.findAllByProductId(id);

        if(sellerProducts == null)
            return new ArrayList<>();

        sellerProducts.stream().forEach(sellerProduct -> sellers.add(sellerProduct.getSeller()));
        return sellers;
    }

    public List<Double> getPrices(int id) {
        List<Double> prices = new ArrayList<>();
        List<SellerProduct> sellerProducts = sellerProductRepository.findAllByProductId(id);

        if(sellerProducts == null)
            return new ArrayList<>();

        sellerProducts.stream().sorted((s1, s2) -> (int) (s1.getPrice() - s2.getPrice())).forEach(sellerProduct -> prices.add(sellerProduct.getPrice()));
        return prices;
    }

    public List<SellerProduct> getSellersPrices(int id) {
        List<SellerProduct> sellerProducts = new ArrayList<>();
        Product product = getById(id);

        if(product == null)
            return new ArrayList<>();

        sellerProducts = product.getSellerProducts();
        return sellerProducts;
    }

    public List<Order> getOrders(int id) {
        Product product = getById(id);

        if (product == null)
            return new ArrayList<>();

        return product.getOrders();
    }

    public List<Delivery> getDeliveries(int id) {
        List<Delivery> deliveries = new ArrayList<>();
        Product product = getById(id);
        List<Order> orders = new ArrayList<>();

        if (product == null)
            return new ArrayList<>();

        orders = product.getOrders();

        orders.stream().forEach(order -> deliveries.add(order.getDelivery()));
        return deliveries;
    }

    public List<Customer> getCustomers(int id) {
        List<Customer> customers = new ArrayList<>();
        Product product = getById(id);
        List<Order> orders = new ArrayList<>();

        if(product == null)
            return new ArrayList<>();

        orders = product.getOrders();
        orders.stream().forEach(order -> customers.add(order.getCustomer()));
        return customers;
    }

    @Transactional
    public Category addCategory(int id, int categoryId) {
        Product product = getById(id);
        Category category = categoryRepository.getById(categoryId);

        if(product == null || category == null)
            return null;

        product.setCategory(category);
        return category;
    }

    @Transactional
    public Order addOrder(int id) {
        Order order = new Order();
        Product product = getById(id);

        if(product == null)
            return null;

        product.getOrders().add(order);
        order.setProduct(product);
        return order;
    }
}
