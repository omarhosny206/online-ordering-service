package com.example.service;

import com.example.dto.SellerProductDto;
import com.example.dto.UpdateSellerProductDto;
import com.example.entity.SellerProduct;
import com.example.entity.User;
import com.example.util.SellerProductId;

import java.util.List;

public interface SellerProductService {
    List<SellerProduct> getAll();

    List<SellerProduct> getAllBySellerId(long sellerId);

    List<SellerProduct> getAllByProductId(long productId);

    SellerProduct getById(SellerProductId sellerProductId);

    List<SellerProduct> getAllByIds(List<SellerProductId> sellerProductIds);

    SellerProduct save(SellerProduct sellerProduct);

    SellerProduct save(User authenticatedSeller, SellerProductDto sellerProductDto);

    SellerProduct update(User authenticatedSeller, long productId, UpdateSellerProductDto updateSellerProductDto);

    void deleteByProductId(User authenticatedSeller, long productId);
}
