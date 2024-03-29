package com.vijay.categoryservice.repository;

import com.vijay.categoryservice.entity.Category;
import com.vijay.commonservice.product.model.CategoryResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This interface extends MongoDBRepository which provides basic CRUD operations for Category entities
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    // This method finds a category by its unique categoryId
    Category findByCategoryId(String categoryId);

    List<Category> findByUserId(String userId);

    // Add additional custom queries here if needed
}
