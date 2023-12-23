package com.example.service.impl;

import com.example.entity.*;
import com.example.repository.OrderRepository;
import com.example.service.*;
import com.example.util.ApiError;
import com.example.util.SellerProductId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartProductService cartProductService;
    private final SellerProductService sellerProductService;
    private final OrderProductService orderProductService;
    private final DeliveryService deliveryService;

    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, CartProductService cartProductService, SellerProductService sellerProductService, OrderProductService orderProductService, DeliveryService deliveryService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartProductService = cartProductService;
        this.sellerProductService = sellerProductService;
        this.orderProductService = orderProductService;
        this.deliveryService = deliveryService;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getByIdCustomerId(long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Order getById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> ApiError.notFound("Order not found with id=" + id));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order save(User authenticatedCustomer) {
        Order order = new Order(authenticatedCustomer);
        Order savedOrder = save(order);
        Cart cart = cartService.getByCustomerId(authenticatedCustomer.getId());
        List<CartProduct> cartProducts = cartProductService.getAllByCartId(cart.getId());
        if (cartProducts.isEmpty()) {
            throw ApiError.conflict("Cannot create an order, cart is empty");
        }
        List<SellerProduct> sellerProducts = sellerProductService.getAllByIds(cartProducts
                .stream()
                .map(cartProduct -> new SellerProductId(cartProduct.getCartProductId().getSellerId(), cartProduct.getCartProductId().getProductId()))
                .toList()
        );
        orderProductService.saveAll(savedOrder, cartProducts, sellerProducts);
        double totalPrice = getTotalPrice(cartProducts, sellerProducts);
        savedOrder.setTotalPrice(totalPrice);
        savedOrder = save(savedOrder);
        cartService.clear(authenticatedCustomer, cart);
        return savedOrder;
    }

    @Override
    @Transactional
    public void deleteById(User authenticatedCustomer, long id) {
        Order order = getById(id);
        checkAuthority(authenticatedCustomer, order);
        if (deliveryService.existsByOrderId(order.getId())) {
            throw ApiError.conflict("Cannot delete an order that will be delivered soon.");
        }
        orderProductService.deleteAllByOrderId(id);
        orderRepository.deleteById(id);
    }

    @Override
    public double getTotalPrice(List<CartProduct> cartProducts, List<SellerProduct> sellerProducts) {
        double totalPrice = 0.0;
        for (int i = 0; i < cartProducts.size(); ++i) {
            totalPrice += sellerProducts.get(i).getPrice() * cartProducts.get(i).getQuantity();
        }
        return totalPrice;
    }

    @Override
    public void checkAuthority(User authenticatedCustomer, Order order) {
        if (authenticatedCustomer.getId() != order.getCustomer().getId()) {
            throw ApiError.forbidden("Cannot do this action, you are not the author.");
        }
    }
}
