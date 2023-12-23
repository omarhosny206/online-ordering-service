package com.example.repository;

import com.example.entity.CartProduct;
import com.example.util.CartProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {
    List<CartProduct> findAllByCartId(long cartId);

    void deleteAllByCartId(long cartId);
}
