package com.example.entity;

import com.example.util.SellerProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "`seller_product`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerProduct implements Serializable {
    @EmbeddedId
    private SellerProductId sellerProductId;

    @ManyToOne
    @MapsId("sellerId")
    private User seller;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    @Column(nullable = false)
    private double price;
}
