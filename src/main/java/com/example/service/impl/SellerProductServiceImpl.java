package com.example.service.impl;

import com.example.dto.SellerProductDto;
import com.example.dto.UpdateSellerProductDto;
import com.example.entity.Product;
import com.example.entity.SellerProduct;
import com.example.entity.User;
import com.example.repository.SellerProductRepository;
import com.example.service.ProductService;
import com.example.service.SellerProductService;
import com.example.util.ApiError;
import com.example.util.SellerProductId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerProductServiceImpl implements SellerProductService {
    private final SellerProductRepository sellerProductRepository;
    private final ProductService productService;

    public SellerProductServiceImpl(SellerProductRepository sellerProductRepository, ProductService productService) {
        this.sellerProductRepository = sellerProductRepository;
        this.productService = productService;
    }

    @Override
    public List<SellerProduct> getAll() {
        return sellerProductRepository.findAll();
    }

    @Override
    public List<SellerProduct> getAllBySellerId(long sellerId) {
        return sellerProductRepository.findAllBySellerId(sellerId);
    }

    @Override
    public List<SellerProduct> getAllByProductId(long productId) {
        return sellerProductRepository.findAllByProductId(productId);
    }

    @Override
    public SellerProduct getById(SellerProductId sellerProductId) {
        return sellerProductRepository.findById(sellerProductId).orElseThrow(() -> ApiError.notFound("SellerProduct not found with id=" + sellerProductId));
    }

    @Override
    public List<SellerProduct> getAllByIds(List<SellerProductId> sellerProductIds) {
        return sellerProductRepository.findAllById(sellerProductIds);
    }

    @Override
    public SellerProduct save(SellerProduct sellerProduct) {
        return sellerProductRepository.save(sellerProduct);
    }

    @Override
    public SellerProduct save(User authenticatedSeller, SellerProductDto sellerProductDto) {
        Product product = productService.getByName(sellerProductDto.getProductName());
        SellerProductId sellerProductId = new SellerProductId(authenticatedSeller.getId(), product.getId());
        SellerProduct sellerProduct = new SellerProduct(sellerProductId, authenticatedSeller, product, sellerProductDto.getPrice());
        return save(sellerProduct);
    }

    @Override
    public SellerProduct update(User authenticatedSeller, long productId, UpdateSellerProductDto updateSellerProductDto) {
        SellerProduct sellerProduct = getById(new SellerProductId(authenticatedSeller.getId(), productId));
        sellerProduct.setPrice(updateSellerProductDto.getPrice());
        return save(sellerProduct);
    }

    @Override
    public void deleteByProductId(User authenticatedSeller, long productId) {
        SellerProduct sellerProduct = getById(new SellerProductId(authenticatedSeller.getId(), productId));
        sellerProductRepository.delete(sellerProduct);
    }
}
