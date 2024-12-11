package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuSalesDto {
    private String menuCode;
    private String menuName;
    private Integer menuPrice;
    private Integer menuCategoryNum;
    private String menuCategoryName;
    private Integer salesCount;
    private Integer salesAmount;
}
