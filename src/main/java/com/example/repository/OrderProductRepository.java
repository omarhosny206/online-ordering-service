package com.example.repository;

import com.example.entity.OrderProduct;
import com.example.util.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findAllByOrderId(long orderId);

    void deleteAllByOrderId(long orderId);
}
