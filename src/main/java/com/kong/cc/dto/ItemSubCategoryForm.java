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

	private Integer itemCategoryNum;
    private String itemCategoryName;
    
    private String itemCategoryMiddleName;
    private Integer itemCategoryMiddleNum;

}
