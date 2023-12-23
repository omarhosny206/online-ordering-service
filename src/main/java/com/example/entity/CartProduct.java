package com.example.entity;

import com.example.util.CartProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`cart_product`")
public class CartProduct {
    @EmbeddedId
    private CartProductId cartProductId;

    @ManyToOne
    @MapsId("cartId")
    private Cart cart;

    @ManyToOne
    @MapsId("sellerId")
    private User seller;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
