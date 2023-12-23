package com.example.service;

import com.example.dto.CategoryDto;
import com.example.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getById(long id);

    Category getByName(String name);

    Category getByNameOrNull(String name);

    boolean existsByName(String name);

    Category save(Category category);

    Category save(CategoryDto categoryDto);

    Category update(int id, CategoryDto categoryDto);
}
