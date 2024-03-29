package com.vijay.commonservice.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
    private String userId;
}
