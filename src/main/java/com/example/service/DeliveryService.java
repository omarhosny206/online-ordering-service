package com.example.service;

import com.example.dto.DeliveryDto;
import com.example.entity.Delivery;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getAll();

    Delivery getById(long id);

    Delivery getByOrderId(long orderId);

    boolean existsByOrderId(long orderId);

    Delivery save(Delivery delivery);

    Delivery save(DeliveryDto deliveryDto);
}
