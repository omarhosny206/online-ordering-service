package com.example.repository;

import com.example.entity.UserProduct;
import com.example.util.UserProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, UserProductId> {
}
