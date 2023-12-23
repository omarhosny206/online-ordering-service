package com.example.repository;

import com.example.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(long orderId);

    boolean existsByOrderId(long orderId);
}
