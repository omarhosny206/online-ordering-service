package com.example.service;

import com.example.model.Product;
import com.example.model.Seller;
import com.example.model.SellerProduct;
import com.example.model.SellerProductId;
import com.example.repository.ProductRepository;
import com.example.repository.SellerProductRepository;
import com.example.repository.SellerRepository;
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

    public List<SellerProduct> getAll() {
        return sellerProductRepository.findAll();
    }

    public SellerProduct getById(int sellerId, int productId) {
        return sellerProductRepository.findById(new SellerProductId(sellerId, productId)).orElse(null);
    }

    public void save(SellerProduct sellerProduct) {
        Seller seller = sellerRepository.findById(sellerProduct.getSellerProductId().getSellerId()).orElse(null);
        Product product = productRepository.findById(sellerProduct.getSellerProductId().getProductId()).orElse(null);
        if (seller == null || product == null)
            return;
        sellerProduct.setSeller(seller);
        sellerProduct.setProduct(product);
        sellerProductRepository.save(sellerProduct);
    }

    @Transactional
    public void update(int sellerId, int productId, SellerProduct sellerProduct) {
        SellerProduct sellerProductToUpdate = sellerProductRepository
                .findById(new SellerProductId(sellerId, productId))
                .orElse(null);
        if (sellerProductToUpdate == null)
            return;
        sellerProductToUpdate.clone(sellerProduct);
    }

    public void delete(int sellerId, int productId) {
        SellerProductId id = new SellerProductId(sellerId, productId);
        SellerProduct sellerProduct = sellerProductRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller Product with seller id"
                        + sellerId + " and product id " + productId + " does not exist"));
        sellerProductRepository.deleteById(id);
    }

}
