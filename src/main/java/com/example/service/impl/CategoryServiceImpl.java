package com.example.service.impl;

import com.example.dto.CategoryDto;
import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import com.example.util.ApiError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> ApiError.notFound("Category not found with id=" + id));
    }

    @Override
    public Category getByName(String name) {
        Category category = getByNameOrNull(name);
        if (category == null) {
            throw ApiError.notFound("Category not found with name=" + name);
        }
        return category;
    }

    @Override
    public Category getByNameOrNull(String name) {
        Category category = categoryRepository.findByName(name);
        return category;
    }

    @Override
    public boolean existsByName(String name) {
        return getByNameOrNull(name) != null;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category save(CategoryDto categoryDto) {
        boolean existsByName = existsByName(categoryDto.getName());
        if (existsByName) {
            throw ApiError.conflict("Category already exists with name=" + categoryDto.getName());
        }
        Category category = new Category(categoryDto.getName());
        return save(category);
    }

    @Override
    public Category update(int id, CategoryDto categoryDto) {
        Category category = getById(id);
        category.setName(categoryDto.getName());
        return save(category);
    }
}
