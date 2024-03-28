package com.vijay.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private String categoryId;


    private String title;



    private String description;

    private String coverImage;

}
