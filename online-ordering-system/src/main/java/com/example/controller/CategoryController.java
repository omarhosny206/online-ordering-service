package com.example.controller;

import com.example.model.Category;
import com.example.model.Product;
import com.example.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<Category> getAll(){
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id){
        return categoryService.getById(id);
    }


    @PostMapping("/")
    public Category save(@RequestBody Category category){
        return categoryService.save(category);
    }

    @PutMapping("/")
    public Category update(@RequestBody Category category){
        return categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        categoryService.delete(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable int id) {
        return categoryService.getProducts(id);
    }
}
