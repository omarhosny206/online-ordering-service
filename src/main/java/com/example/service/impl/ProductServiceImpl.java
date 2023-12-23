package com.example.service.impl;

import com.example.dto.ProductDto;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.example.util.ApiError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UserService userService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(long id) {
        return productRepository.findById(id).orElseThrow(() -> ApiError.notFound("Product not found with id=" + id));
    }

    @Override
    public Product getByName(String name) {
        Product product = getByNameOrNull(name);
        if (product == null) {
            throw ApiError.notFound("Product not found with name=" + name);
        }
        return product;
    }

    @Override
    public Product getByNameOrNull(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product save(ProductDto productDto) {
        Product savedProduct = getByNameOrNull(productDto.getName());
        if (savedProduct != null) {
            throw ApiError.conflict("Product already exists by this name");
        }
        Category category = categoryService.getById(productDto.getCategoryId());
        Product product = new Product(productDto.getName(), category);
        return save(product);
    }

    @Override
    public Product update(long id, ProductDto productDto) {
        Product storedProduct = getById(id);
        Category category = (storedProduct.getCategory().getId() == productDto.getCategoryId()) ? storedProduct.getCategory() : categoryService.getById(productDto.getCategoryId());
        storedProduct.setName(productDto.getName());
        storedProduct.setCategory(category);
        return save(storedProduct);
    }

    @Override
    public void deleteById(long id) {
        Product product = getById(id);
        productRepository.delete(product);
    }
}
