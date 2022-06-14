package com.example.service;

import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(int id) {
        Category category = getById(id);
        if (category == null)
            throw new IllegalArgumentException("Category with id " + id + " does not exist");
        category.getProducts().forEach(p -> p.setCategory(null));
        categoryRepository.deleteById(id);
    }

    public List<Product> getProducts(int id) {
        Category category = getById(id);

        if (category == null)
            return new ArrayList<>();

        return category.getProducts();
    }


}
