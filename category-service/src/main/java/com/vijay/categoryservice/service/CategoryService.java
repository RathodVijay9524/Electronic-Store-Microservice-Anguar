package com.vijay.categoryservice.service;

import com.vijay.commonservice.category.model.CategoryRequest;
import com.vijay.commonservice.category.model.CategoryResponse;

import java.util.List;

public interface CategoryService {

    // Create operation - Create a new category
    CategoryResponse createCategory(CategoryRequest category);

    // Read operation - Retrieve a category by its categoryId
    CategoryResponse getCategoryById(String categoryId);

    // Read operation - Retrieve all categories
    List<CategoryResponse> getAllCategories();

    // Update operation - Update an existing category
    CategoryResponse updateCategory(String categoryId, CategoryRequest updatedCategory);

    // Delete operation - Delete a category by its categoryId
    void deleteCategory(String categoryId);

    List<CategoryResponse> getCategoryByUserId(String userId);

    CategoryResponse getCategoryByIdForProductList(String categoryId);





}

