package com.example.controller;

import com.example.dto.CategoryDto;
import com.example.entity.Category;
import com.example.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("hasAnyAuthority('admin', 'seller')")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getById(id));
    }


    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Category> save(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.save(categoryDto));
    }
}
