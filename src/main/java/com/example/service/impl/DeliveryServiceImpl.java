package com.example.service.impl;

import com.example.dto.DeliveryDto;
import com.example.entity.Delivery;
import com.example.entity.Order;
import com.example.repository.DeliveryRepository;
import com.example.service.DeliveryService;
import com.example.service.OrderService;
import com.example.util.ApiError;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderService orderService;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, @Lazy OrderService orderService) {
        this.deliveryRepository = deliveryRepository;
        this.orderService = orderService;
    }

    @Override
    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery getById(long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> ApiError.notFound("Delivery not found with id=" + id));
    }

    @Override
    public Delivery getByOrderId(long orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId);
        if (delivery == null) {
            throw ApiError.notFound("Delivery not found with orderId=" + orderId);
        }
        return delivery;
    }

    @Override
    public boolean existsByOrderId(long orderId) {
        return deliveryRepository.existsByOrderId(orderId);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery save(DeliveryDto deliveryDto) {
        Order order = orderService.getById(deliveryDto.getOrderId());
        boolean existsByOrderId = existsByOrderId(deliveryDto.getOrderId());
        if (existsByOrderId) {
            throw ApiError.conflict("Delivery already exists for this order");
        }
        Delivery delivery = new Delivery(order);
        return save(delivery);
    }
}