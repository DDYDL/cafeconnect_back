package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemExpenseDto {
//통계데이터를 위한 DTO (Map<List<Map<String,Object>>> 싫어서 만듦)
    private String itemCode;
    private String itemName;
    private Integer itemPrice;
    private Integer totalOrderCount;
    private Integer totalOrderPrice;
    private String majorCategoryName;
    private Integer majorCategoryNum;
    private String middleCategoryName;
    private Integer middleCategoryNum;
    private String subCategoryName;
    private Integer subCategoryNum;
    
    private long rowspanCount; 
    
}
