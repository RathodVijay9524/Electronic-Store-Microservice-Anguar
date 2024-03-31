package com.vijay.categoryservice.service;

import com.vijay.categoryservice.client.ProductFeignClient;
import com.vijay.categoryservice.client.UserFeignClient;
import com.vijay.categoryservice.entity.Category;
import com.vijay.commonservice.category.model.CategoryRequest;
import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.categoryservice.repository.CategoryRepository;

import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import com.vijay.commonservice.user.response.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service implementation for handling category-related operations.
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;

    /**
     * Create a new category.
     * @param categoryRequest The request object containing category details.
     * @return The response object representing the created category.
     */
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // Create category entity from request
        Category category = Category.builder()
                .title(categoryRequest.getTitle())
                .description(categoryRequest.getDescription())
                .coverImage(categoryRequest.getCoverImage())
                .userId(categoryRequest.getUserId())
                .build();
        // Save the category to the database
        Category savedCategory = categoryRepository.save(category);
        // Map the saved category to response object and return
        return mapToResponse(savedCategory);
    }

    /**
     * Retrieve a category by its ID.
     * @param categoryId The ID of the category to retrieve.
     * @return The response object representing the retrieved category.
     * @throws RuntimeException if the category is not found.
     */
    @Override
    public CategoryResponse getCategoryById(String categoryId) {
        // Retrieve category from repository by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // Fetch user information using Feign client
        UserDto user = userFeignClient.getUser(category.getUserId());
        category.setUser(user); // Set the user information in the category
        // Map the category to response object and return
        return mapToResponse(category);
    }

    /**
     * Retrieve all categories.
     * @return List of response objects representing all categories.
     */
    @Override
    public List<CategoryResponse> getAllCategories() {
        // Retrieve all categories from repository
        List<Category> categories = categoryRepository.findAll();
        // Map each category to response object and collect into a list
        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing category.
     * @param categoryId The ID of the category to update.
     * @param updatedCategory The updated details of the category.
     * @return The response object representing the updated category.
     * @throws RuntimeException if the category is not found.
     */
    @Override
    public CategoryResponse updateCategory(String categoryId, CategoryRequest updatedCategory) {
        // Retrieve category from repository by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // Update category details from request
        category.setTitle(updatedCategory.getTitle());
        category.setDescription(updatedCategory.getDescription());
        category.setCoverImage(updatedCategory.getCoverImage());
        //category.setUserId(updatedCategory.getUserId()); // Assuming userId cannot be updated
        // Save the updated category to the database
        Category updatedCategoryEntity = categoryRepository.save(category);
        // Map the updated category to response object and return
        return mapToResponse(updatedCategoryEntity);
    }

    /**
     * Delete a category by its ID.
     * @param categoryId The ID of the category to delete.
     */
    @Override
    public void deleteCategory(String categoryId) {
        // Delete category from repository by ID
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Retrieve all categories belonging to a specific user.
     * @param userId The ID of the user.
     * @return List of response objects representing categories owned by the user.
     */
    @Override
    public List<CategoryResponse> getCategoryByUserId(String userId) {
        // Retrieve categories from repository by user ID
        List<Category> categories = categoryRepository.findByUserId(userId);

        categories.forEach(category -> {
            List<ProductResponse> list= productFeignClient.getProductsByCategoryId(category.getCategoryId());
            category.setProducts(list);
        });
        // Map each category to response object and collect into a list
        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves category information along with associated List<products> and user details.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A response containing the category details, associated products, and user information.
     * @throws RuntimeException if the category is not found.
     */
    @Override
    public CategoryResponse getCategoryByIdForProductList(String categoryId) {
        // Retrieve the category by its ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // Retrieve products associated with the category using Feign client
        List<ProductResponse> productsByCategoryId= productFeignClient.getProductsByCategoryId(category.getCategoryId());
        // Set the retrieved products to the category
        category.setProducts(productsByCategoryId);
        // Retrieve user details associated with the category using Feign client
        UserDto userByUserId= userFeignClient.getUserByUserId(category.getUserId());
        // Set the retrieved user details to the category
        category.setUser(userByUserId);
        // Map the category to a response object and return
        return mapToResponse(category);
    }


    /**
     * Map Category entity to CategoryResponse DTO.
     * @param category The Category entity to map.
     * @return The mapped CategoryResponse DTO.
     */
    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .title(category.getTitle())
                .description(category.getDescription())
                .coverImage(category.getCoverImage())
                .userId(category.getUserId())
                .products(category.getProducts())
                .user(category.getUser()) // Assuming user information is already set
                .build();
    }
}
