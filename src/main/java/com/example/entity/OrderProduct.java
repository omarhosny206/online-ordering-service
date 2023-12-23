package com.example.entity;

import com.example.util.OrderProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "`order_product`")
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
    @MapsId("sellerId")
    private User seller;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    public OrderProduct(OrderProductId orderProductId, Order order, User seller, Product product, double price) {
        this.orderProductId = orderProductId;
        this.order = order;
        this.seller = seller;
        this.product = product;
        this.price = price;
    }
}
