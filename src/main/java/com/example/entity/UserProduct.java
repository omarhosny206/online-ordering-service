package com.example.entity;

import com.example.util.UserProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "``user_product`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProduct implements Serializable {
    @EmbeddedId
    private UserProductId userProductId;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private double price;

    public void clone(UserProduct userProduct) {
        if (userProduct.price >= 0 && userProduct.price != this.price)
            this.price = userProduct.price;
    }
}
