package com.vijay.productservice.service;

import com.vijay.commonservice.user.exception.ResourceNotFoundException;
import com.vijay.productservice.entity.Product;
import com.vijay.productservice.model.ProductRequest;
import com.vijay.productservice.model.ProductResponse;
import com.vijay.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductService interface for handling product-related operations.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ProductResponse> findProductsByCategoryId(String categoryId) {
        // Retrieve products associated with the given category ID from the repository
        List<Product> products = productRepository.findByCategoryId(categoryId);

        // Map each Product entity to ProductResponse using ModelMapper and collect them into a list
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findProductsByUser(String userId) {
        // Retrieve products associated with the given user ID from the repository
        List<Product> products = productRepository.findByUserId(userId);

        // Map each Product entity to ProductResponse using ModelMapper and collect them into a list
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new product.
     *
     * @param productRequest The request containing details of the product to be created.
     * @return The response containing details of the created product.
     */
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Map the ProductRequest to Product entity and save it to the repository
        Product product = modelMapper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        // Map the saved Product entity back to ProductResponse and return it
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The response containing details of the retrieved product.
     * @throws ResourceNotFoundException if the product with the specified ID is not found.
     */
    @Override
    public ProductResponse getProductById(String productId) {
        // Retrieve the product by ID from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Map the retrieved Product entity to ProductResponse and return it
        return modelMapper.map(product, ProductResponse.class);
    }

    /**
     * Retrieves all products.
     *
     * @return A list of responses containing details of all products.
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        // Retrieve all products from the repository
        List<Product> products = productRepository.findAll();
        // Map each Product entity to ProductResponse and collect them into a list
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing product.
     *
     * @param productId      The ID of the product to update.
     * @param updatedProduct The request containing updated details of the product.
     * @return The response containing details of the updated product.
     * @throws ResourceNotFoundException if the product with the specified ID is not found.
     */
    @Override
    public ProductResponse updateProduct(String productId, ProductRequest updatedProduct) {
        // Retrieve the existing product from the repository
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Map the updated details from ProductRequest to the existing Product entity
        modelMapper.map(updatedProduct, existingProduct);
        existingProduct.setProductId(productId);
        // Save the updated product to the repository
        Product savedProduct = productRepository.save(existingProduct);
        // Map the saved Product entity back to ProductResponse and return it
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @throws ResourceNotFoundException if the product with the specified ID is not found.
     */
    @Override
    public void deleteProduct(String productId) {
        // Retrieve the product by ID from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        // Delete the product from the repository
        productRepository.delete(product);
    }


}

