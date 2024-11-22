package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
	private String itemCode;

	private String itemName;
	private Integer itemPrice;
	private String itemCapacity;
	private Integer itemUnitQuantity;
	private String itemUnit;
	private String itemStandard;
	private String itemStorage;
	private String itemCountryOrigin;

	private Integer itemMajorCategoryNum;
	private Integer itemMiddleCategoryNum;
	private Integer itemSubCategoryNum;
	private Integer itemFileNum;
}
