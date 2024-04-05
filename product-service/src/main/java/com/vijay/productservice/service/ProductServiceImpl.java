package com.vijay.productservice.service;

import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.commonservice.product.model.ProductRequest;
import com.vijay.commonservice.product.model.ProductResponse;

import com.vijay.commonservice.user.response.UserDto;
import com.vijay.commonservice.user.response.UserResponse;
import com.vijay.productservice.client.CategoryServiceFeignClient;

import com.vijay.productservice.client.UserFeignClient;
import com.vijay.productservice.entity.Product;
import com.vijay.productservice.repository.ProductRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    // ProductRepository for accessing product data
    private final ProductRepository productRepository;

    // Feign client for accessing category service
    private final CategoryServiceFeignClient categoryServiceFeignClient;
    private final UserFeignClient userFeignClient;

    // Method to reduce the quantity of a product
    @Override
    public void reduceQuantity(String productId, long quantity) {
        // Retrieve product by ID from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if available quantity is sufficient
        int currentQuantity = product.getQuantity();
        if (currentQuantity < quantity) {
            throw new RuntimeException("Insufficient quantity");
        }

        // Reduce the quantity and save the updated product
        product.setQuantity((int) (currentQuantity - quantity));
        productRepository.save(product);
    }

    @Override
    public void increaseQuantity(String productId, long quantity) {
        // Retrieve product by ID from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Increase the quantity and save the updated product
        int currentQuantity = product.getQuantity();
        product.setQuantity((int) (currentQuantity + quantity));
        System.out.println("product Quantity incrissed");
        productRepository.save(product);
    }


    // Method to create a new product
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Create a new product entity using the request data
        Product product = Product.builder()
                .title(productRequest.getTitle())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .discountedPrice(productRequest.getDiscountedPrice())
                .quantity(productRequest.getQuantity())
                .addedDate(new Date())
                .live(productRequest.isLive())
                .stock(productRequest.isStock())
                .categoryId(productRequest.getCategoryId())
                .userId(productRequest.getUserId())
                .productImageName(productRequest.getProductImageName())
                .build();

        // Save the newly created product and return its response
        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    // Method to retrieve product by ID
    @Override
    public ProductResponse getProductById(String productId) {
        // Retrieve product by ID from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Retrieve category information for the product
      /*  CategoryResponse category= categoryServiceFeignClient.getCategoryByCategoryId(product.getCategoryId());
        // Set the category for the product and return its response
        product.setCategory(category);*/
        return mapToResponse(product);
    }

    // Method to retrieve all products along with user details
    @Override
    public List<ProductResponse> getAllProducts() {
        // Retrieve all products from the repository
        List<Product> products = productRepository.findAll();
    /*    products.forEach(product -> {
            UserDto user= userFeignClient.getUser(product.getUserId());
            product.setUser(user);
        });*/
        // Map the products to response objects and return
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Method to update an existing product
    @Override
    public ProductResponse updateProduct(String productId, ProductRequest updatedProduct) {
        // Retrieve product by ID from the repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Retrieve category information for the updated product
        CategoryResponse category= categoryServiceFeignClient.getCategoryByCategoryId(updatedProduct.getCategoryId());

        // Update product fields with new values from the request
        product.setTitle(updatedProduct.getTitle());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setDiscountedPrice(updatedProduct.getDiscountedPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setAddedDate(updatedProduct.getAddedDate());
        product.setLive(updatedProduct.isLive());
        product.setStock(updatedProduct.isStock());
        product.setProductImageName(updatedProduct.getProductImageName());
        product.setCategory(category);

        // Save the updated product and return its response
        Product updatedProductEntity = productRepository.save(product);
        return mapToResponse(updatedProductEntity);
    }

    // Method to delete a product by ID
    @Override
    public void deleteProduct(String productId) {
        // Delete product by ID from the repository
        productRepository.deleteById(productId);
    }

    // Method to find products by category ID
    @Override
    public List<ProductResponse> findProductsByCategoryId(String categoryId) {
        // Retrieve products by category ID from the repository and map them to responses
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Method to find products by user ID
    @Override
    public List<ProductResponse> findProductsByUser(String userId) {
        // Retrieve products by user ID from the repository and map them to responses
        List<Product> products = productRepository.findByUserId(userId);
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Method to map a product entity to its response representation
    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productId(String.valueOf(product.getProductId()))
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountedPrice(product.getDiscountedPrice())
                .quantity(product.getQuantity())
                .addedDate(product.getAddedDate())
                .live(product.isLive())
                .stock(product.isStock())
                .categoryId(product.getCategoryId())
                .userId(product.getUserId())
                .category(product.getCategory())
                .user(product.getUser())
                .productImageName(product.getProductImageName())
                .build();
    }
}
