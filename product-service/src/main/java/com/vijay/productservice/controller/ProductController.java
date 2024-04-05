package com.vijay.productservice.controller;

import com.vijay.commonservice.product.model.ProductRequest;
import com.vijay.commonservice.product.model.ProductResponse;
import com.vijay.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    // Logger for logging information
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // ProductService for handling product-related operations
    private final ProductService productService;

  // Endpoint to fetch products by category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String categoryId) {
        logger.info("Fetching products by category with ID: {}", categoryId);
        // Retrieve products by category ID
        List<ProductResponse> products = productService.findProductsByCategoryId(categoryId);
        logger.info("Found {} products for category ID: {}", products.size(), categoryId);
        return ResponseEntity.ok(products);
    }

    // Endpoint to fetch products by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponse>> getProductsByUser(@PathVariable String userId) {
        logger.info("Fetching products by user with ID: {}", userId);
        // Retrieve products by user ID
        List<ProductResponse> products = productService.findProductsByUser(userId);
        logger.info("Found {} products for user ID: {}", products.size(), userId);
        return ResponseEntity.ok(products);
    }

    // Endpoint to create a new product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        logger.info("Creating a new product: {}", productRequest);
        // Create a new product
        ProductResponse createdProduct = productService.createProduct(productRequest);
        logger.info("Product created successfully: {}", createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // Endpoint to retrieve a product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId) {
        logger.info("Retrieving product with ID: {}", productId);
        // Retrieve product by ID
        ProductResponse product = productService.getProductById(productId);
        logger.info("Product retrieved successfully: {}", product);
        return ResponseEntity.ok(product);
    }

    // Endpoint to retrieve all products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        logger.info("Retrieving all products");
        // Retrieve all products
        List<ProductResponse> products = productService.getAllProducts();
        logger.info("Products retrieved successfully: {}", products);
        return ResponseEntity.ok(products);
    }

    // Endpoint to update a product by ID
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String productId,
            @RequestBody ProductRequest updatedProductRequest
    ) {
        logger.info("Updating product with ID: {}", productId);
        // Update product by ID
        ProductResponse updatedProduct = productService.updateProduct(productId, updatedProductRequest);
        logger.info("Product updated successfully: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    // Endpoint to delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        logger.info("Deleting product with ID: {}", productId);
        // Delete product by ID
        productService.deleteProduct(productId);
        logger.info("Product deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/increaseQuantity/{productId}")
    public ResponseEntity<String> increaseProductQuantity(
            @RequestParam long quantity,
            @PathVariable("productId") String productId) {
        try {
            productService.increaseQuantity(productId, quantity);
            return new ResponseEntity<>("Product quantity increased successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to reduce the quantity of a product
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<String> reduceProductQuantity(
            @RequestParam long quantity,
            @PathVariable("id") String productId) {
        try {
            // Reduce the quantity of the product
            productService.reduceQuantity(productId, quantity);
            logger.info("Quantity of product with ID {} reduced by {}", productId, quantity);
            return ResponseEntity.ok("Quantity reduced successfully");
        } catch (RuntimeException e) {
            logger.error("Error reducing quantity of product with ID {}: {}", productId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

