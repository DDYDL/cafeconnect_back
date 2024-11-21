package com.kong.cc.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMiddleCategoryForm {

    private String itemCategoryName;
    private String itemCategoryMajorName;

}
