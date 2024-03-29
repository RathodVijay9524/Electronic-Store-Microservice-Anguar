package com.vijay.categoryservice.repository;

import com.vijay.categoryservice.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

// This interface extends MongoDBRepository which provides basic CRUD operations for Category entities
public interface CategoryRepository extends MongoRepository<Category, String> {

    // This method finds a category by its unique categoryId
    Category findByCategoryId(String categoryId);

    // Add additional custom queries here if needed
}
