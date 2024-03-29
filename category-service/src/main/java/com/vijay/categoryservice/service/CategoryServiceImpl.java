package com.vijay.categoryservice.service;

import com.vijay.categoryservice.entity.Category;
import com.vijay.commonservice.product.model.CategoryRequest;
import com.vijay.commonservice.product.model.CategoryResponse;
import com.vijay.categoryservice.repository.CategoryRepository;

import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service implementation for handling category-related operations.
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new category.
     *
     * @param categoryRequest The details of the category to be created.
     * @return CategoryResponse representing the created category.
     */
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // Map CategoryRequest to Category entity
        Category category = modelMapper.map(categoryRequest, Category.class);

        // Save the category entity
        Category savedCategory = categoryRepository.save(category);

        // Map the saved category entity back to CategoryResponse
        return modelMapper.map(savedCategory, CategoryResponse.class);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryId ID of the category to retrieve.
     * @return CategoryResponse representing the category with the specified ID.
     * @throws ResourceNotFoundException if the category with the specified ID is not found.
     */
    @Override
    public CategoryResponse getCategoryById(String categoryId) {
        // Find category by categoryId
        Category category = categoryRepository.findByCategoryId(categoryId);

        // If category not found, throw ResourceNotFoundException
        if (category == null) {
            throw new ResourceNotFoundException("Category", "Id", categoryId);
        }

        // Map category entity to CategoryResponse and return
        return modelMapper.map(category, CategoryResponse.class);
    }

    /**
     * Retrieves all categories.
     *
     * @return List of CategoryResponse representing all categories.
     */
    @Override
    public List<CategoryResponse> getAllCategories() {
        // Retrieve all categories from the database
        List<Category> categories = categoryRepository.findAll();

        // Map each Category entity to CategoryResponse and collect into a list
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
    }

    /**
     * Updates an existing category.
     *
     * @param categoryId       ID of the category to update.
     * @param updatedCategory Details of the category with updated information.
     * @return CategoryResponse representing the updated category.
     * @throws ResourceNotFoundException if the category with the specified ID is not found.
     */
    @Override
    public CategoryResponse updateCategory(String categoryId, CategoryRequest updatedCategory) {
        // Find the existing category by categoryId
        Category existingCategory = categoryRepository.findByCategoryId(categoryId);
        // If category not found, throw ResourceNotFoundException
        if (existingCategory == null) {
            throw new ResourceNotFoundException("Category", "Id", categoryId);
        }
        // Map fields from updatedCategory to the existing Category entity
        modelMapper.map(updatedCategory, existingCategory);
        existingCategory.setCategoryId(categoryId);

        // Save the updated category
        Category savedCategory = categoryRepository.save(existingCategory);
        // Map the saved category back to CategoryResponse and return
        return modelMapper.map(savedCategory, CategoryResponse.class);
    }
    /**
     * Deletes a category by its ID.
     *
     * @param categoryId ID of the category to delete.
     * @throws ResourceNotFoundException if the category with the specified ID is not found.
     */
    @Override
    public void deleteCategory(String categoryId) {
        // Find the category by categoryId
        Category category = categoryRepository.findByCategoryId(categoryId);
        // If category not found, throw ResourceNotFoundException
        if (category == null) {
            throw new ResourceNotFoundException("Category", "Id", categoryId);
        }
        // Delete the category
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> getCategoryByUserId(String userId) {
        // Retrieve categories by user ID from the repository
        List<Category> categories = categoryRepository.findByUserId(userId);

        // Map Category objects to CategoryResponse objects using ModelMapper and Java 8 streams
        List<CategoryResponse> categoryResponses = categories.stream()
                // Map each Category object to a CategoryResponse object
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                // Collect the mapped objects into a list
                .toList();
        // Return the list of CategoryResponse objects
        return categoryResponses;
    }
}
