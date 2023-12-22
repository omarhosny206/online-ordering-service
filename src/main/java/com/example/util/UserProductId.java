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
public class UserProductId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;
}
