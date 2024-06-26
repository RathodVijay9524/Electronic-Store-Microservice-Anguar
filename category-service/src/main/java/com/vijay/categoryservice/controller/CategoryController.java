package com.vijay.categoryservice.controller;

import com.vijay.commonservice.category.model.CategoryRequest;
import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.categoryservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        logger.info("Received request to create a new category");
        CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);
        logger.info("New category created: {}", createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String categoryId) {
        logger.info("Received request to retrieve category by ID: {}", categoryId);
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            logger.info("Category found: {}", category);
            return ResponseEntity.ok(category);
        } else {
            logger.warn("Category not found with ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryByIdForProduct(@PathVariable String categoryId) {
        logger.info("Recived request to retrieve category by ID: {}", categoryId);
        CategoryResponse category = categoryService.getCategoryByIdForProductList(categoryId);
        if (category != null) {
            logger.info("Catgory found: {}", category);
            return ResponseEntity.ok(category);
        } else {
            logger.warn("Category not found wit ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        logger.info("Received request to retrieve all categories");
        List<CategoryResponse> categories = categoryService.getAllCategories();
        logger.info("Total categories retrieved: {}", categories.size());
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryRequest updatedCategoryRequest
    ) {
        logger.info("Received request to update category with ID: {}", categoryId);
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, updatedCategoryRequest);
        if (updatedCategory != null) {
            logger.info("Category updated: {}", updatedCategory);
            return ResponseEntity.ok(updatedCategory);
        } else {
            logger.warn("Categors not found with ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        logger.info("Received request to delete category with ID: {}", categoryId);
        categoryService.deleteCategory(categoryId);
        logger.info("Category deleted with ID: {}", categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryResponse>> getCategoryByUserId(@PathVariable String userId) {
        logger.info("Retrieving categories for user with ID: {}", userId);
        List<CategoryResponse> categories = categoryService.getCategoryByUserId(userId);
        logger.info("Categories retrieved successfully for user with ID: {}", userId);
        return ResponseEntity.ok(categories);
    }
}
