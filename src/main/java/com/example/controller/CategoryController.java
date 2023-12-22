package com.example.controller;

import com.example.entity.Category;
import com.example.entity.Product;
import com.example.service.impl.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @GetMapping("/")
    public List<Category> getAll() {
        return categoryServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        return categoryServiceImpl.getById(id);
    }


    @PostMapping("/")
    public Category save(@RequestBody Category category) {
        return categoryServiceImpl.save(category);
    }

    @PutMapping("/")
    public Category update(@RequestBody Category category) {
        return categoryServiceImpl.update(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryServiceImpl.delete(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable int id) {
        return categoryServiceImpl.getProducts(id);
    }
}
