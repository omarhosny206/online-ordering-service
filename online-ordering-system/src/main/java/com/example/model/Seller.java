package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SellerProduct> sellerProducts;

    public void clone(Seller seller) {
        if(seller.firstName != null && !seller.firstName.isEmpty() && !this.firstName.equalsIgnoreCase(seller.firstName))
            this.firstName = seller.firstName;
        if(seller.lastName != null && !seller.lastName.isEmpty() && !this.lastName.equalsIgnoreCase(seller.lastName))
            this.lastName = seller.lastName;
        if(seller.email != null && !seller.email.isEmpty() && !this.email.equalsIgnoreCase(seller.email))
            this.email = seller.email;
    }
}
