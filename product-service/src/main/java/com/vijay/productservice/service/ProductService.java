package com.vijay.productservice.service;

import com.vijay.productservice.model.ProductRequest;
import com.vijay.productservice.model.ProductResponse;

import java.util.List;

public interface ProductService {
    /**
     * Creates a new product.
     *
     * @param productRequest The request containing details of the product to be created.
     * @return The response containing details of the created product.
     */
    ProductResponse createProduct(ProductRequest productRequest);

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The response containing details of the retrieved product.
     */
    ProductResponse getProductById(String productId);

    /**
     * Retrieves all products.
     *
     * @return A list of responses containing details of all products.
     */
    List<ProductResponse> getAllProducts();

    /**
     * Updates an existing product.
     *
     * @param productId      The ID of the product to update.
     * @param updatedProduct The request containing updated details of the product.
     * @return The response containing details of the updated product.
     */
    ProductResponse updateProduct(String productId, ProductRequest updatedProduct);

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    void deleteProduct(String productId);

    /**
     * Finds products associated with a given category.
     *
     * @param categoryId The ID of the category.
     * @return A list of ProductResponse objects associated with the category.
     */
    List<ProductResponse> findProductsByCategoryId(String categoryId);

    /**
     * Finds products associated with a given user.
     *
     * @param userId The ID of the user.
     * @return A list of ProductResponse objects associated with the user.
     */
    List<ProductResponse> findProductsByUser(String userId);

}
