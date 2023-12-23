package com.example.service;

import com.example.dto.ProductDto;
import com.example.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(long id);

    Product getByName(String name);

    Product getByNameOrNull(String name);

    Product save(Product product);

    Product save(ProductDto productDto);

    Product update(long id, ProductDto productDto);

    void deleteById(long id);
}
