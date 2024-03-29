package com.vijay.productservice.controller;

import com.vijay.productservice.model.ProductRequest;
import com.vijay.productservice.model.ProductResponse;
import com.vijay.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String categoryId) {
        logger.info("Fetching products by category with ID: {}", categoryId);
        List<ProductResponse> products = productService.findProductsByCategoryId(categoryId);
        logger.info("Found {} products for category ID: {}", products.size(), categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponse>> getProductsByUser(@PathVariable String userId) {
        logger.info("Fetching products by user with ID: {}", userId);
        List<ProductResponse> products = productService.findProductsByUser(userId);
        logger.info("Found {} products for user ID: {}", products.size(), userId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        logger.info("Creating a new product: {}", productRequest);
        ProductResponse createdProduct = productService.createProduct(productRequest);
        logger.info("Product created successfully: {}", createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId) {
        logger.info("Retrieving product with ID: {}", productId);
        ProductResponse product = productService.getProductById(productId);
        logger.info("Product retrieved successfully: {}", product);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        logger.info("Retrieving all products");
        List<ProductResponse> products = productService.getAllProducts();
        logger.info("Products retrieved successfully: {}", products);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String productId,
            @RequestBody ProductRequest updatedProductRequest
    ) {
        logger.info("Updating product with ID: {}", productId);
        ProductResponse updatedProduct = productService.updateProduct(productId, updatedProductRequest);
        logger.info("Product updated successfully: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        logger.info("Deleting product with ID: {}", productId);
        productService.deleteProduct(productId);
        logger.info("Product deleted successfully");
        return ResponseEntity.noContent().build();
    }
}

