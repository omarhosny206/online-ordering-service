package com.example.repository;

import com.example.entity.SellerProduct;
import com.example.util.SellerProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, SellerProductId> {
    List<SellerProduct> findAllByProductId(long productId);

    List<SellerProduct> findAllBySellerId(long sellerId);
}
