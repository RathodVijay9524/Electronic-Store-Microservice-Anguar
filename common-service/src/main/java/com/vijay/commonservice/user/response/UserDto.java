package com.vijay.commonservice.user.response;


import com.vijay.commonservice.category.model.CategoryResponse;
import com.vijay.commonservice.product.model.ProductResponse;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;
    private Set<RoleDto> roles = new HashSet<>();

    private List<CategoryResponse> categories;
    private List<ProductResponse> products;
}
