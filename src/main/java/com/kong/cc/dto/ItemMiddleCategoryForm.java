package com.kong.cc.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMiddleCategoryForm {

	private Integer itemCategoryNum;
    private String itemCategoryName;
    
    private Integer itemCategoryMajorNum;
    private String itemCategoryMajorName;
    
    
    private List<ItemSubCategoryForm> subCategories;

}
