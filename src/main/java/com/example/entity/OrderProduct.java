package com.example.entity;

import com.example.util.OrderProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "``order_product`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct implements Serializable {
    @EmbeddedId
    private OrderProductId orderProductId;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private double price;
}
