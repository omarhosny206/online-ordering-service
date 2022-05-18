package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seller_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerProduct implements Serializable {
    @EmbeddedId
    private SellerProductId sellerProductId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @MapsId("sellerId")
    @JsonIgnore
    private Seller seller;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @MapsId("productId")
    @JsonIgnore
    private Product product;

    private double price;
}
