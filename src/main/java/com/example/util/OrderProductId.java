package com.example.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductId implements Serializable {
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "seller_id")
    private long sellerId;

    @Column(name = "product_id")
    private long productId;
}
