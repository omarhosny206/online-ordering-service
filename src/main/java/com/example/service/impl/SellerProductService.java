package com.example.service.impl;

import com.example.entity.Product;
import com.example.entity.UserProduct;
import com.example.repository.ProductRepository;
import com.example.repository.SellerProductRepository;
import com.example.util.UserProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SellerProductService {

    private final SellerProductRepository sellerProductRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SellerProductService(SellerProductRepository sellerProductRepository, SellerRepository sellerRepository, ProductRepository productRepository) {
        this.sellerProductRepository = sellerProductRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    public List<UserProduct> getAll() {
        return sellerProductRepository.findAll();
    }

    public UserProduct getById(int sellerId, int productId) {
        return sellerProductRepository.findById(new UserProductId(sellerId, productId)).orElse(null);
    }

    public void save(UserProduct userProduct) {
        Seller seller = sellerRepository.findById(userProduct.getUserProductId().getSellerId()).orElse(null);
        Product product = productRepository.findById(userProduct.getUserProductId().getProductId()).orElse(null);
        if (seller == null || product == null)
            return;
        userProduct.setSeller(seller);
        userProduct.setProduct(product);
        sellerProductRepository.save(userProduct);
    }

    @Transactional
    public void update(int sellerId, int productId, UserProduct userProduct) {
        UserProduct userProductToUpdate = sellerProductRepository
                .findById(new UserProductId(sellerId, productId))
                .orElse(null);
        if (userProductToUpdate == null)
            return;
        userProductToUpdate.clone(userProduct);
    }

    public void delete(int sellerId, int productId) {
        UserProductId id = new UserProductId(sellerId, productId);
        UserProduct userProduct = sellerProductRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller Product with seller id"
                        + sellerId + " and product id " + productId + " does not exist"));
        sellerProductRepository.deleteById(id);
    }

}
