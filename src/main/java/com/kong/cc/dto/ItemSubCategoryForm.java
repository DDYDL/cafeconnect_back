package com.kong.cc.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSubCategoryForm {

    private String itemCategoryName;
    private Integer itemCategoryMajorNum;

}
