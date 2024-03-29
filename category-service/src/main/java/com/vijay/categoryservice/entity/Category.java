package com.vijay.categoryservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("category_table")
public class Category {

    @Id
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
    private String userId;

}