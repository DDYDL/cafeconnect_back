package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDetailDto {
	private String itemCode;
	private String itemName;
	private Integer itemPrice;
	private Integer salesCount;
	private Long salesAmount;
	private Integer itemMajorCategoryNum;
	private String  itemMajorCategoryName;
	
	private Integer itemMiddleCategoryNum;
	private String  itemMiddleCategoryName;
	
	private Integer itemSubCategoryNum;
	private String 	itemSubCategoryName;
}
