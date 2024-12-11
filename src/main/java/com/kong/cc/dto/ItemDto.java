package com.kong.cc.dto;

import com.kong.cc.entity.*;
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
	private String  itemMajorCategoryName;
	
	private Integer itemMiddleCategoryNum;
	private String  itemMiddleCategoryName;
	
	private Integer itemSubCategoryNum;
	private String 	itemSubCategoryName;
	private Integer itemFileNum;
	private String itemFileName;

	private Integer wishItemNum;
	private Integer orderCount;
	
	public Item toEntity() {
		return Item.builder()
				.itemCode(itemCode)
				.itemName(itemName)
				.itemPrice(itemPrice)
				.itemCapacity(itemCapacity)
				.itemUnitQuantity(itemUnitQuantity)
				.itemUnit(itemUnit)
				.itemStandard(itemStandard)
				.itemStorage(itemStorage)
				.itemCountryOrigin(itemCountryOrigin)
				.itemMajorCategory(ItemMajorCategory.builder().itemCategoryNum(itemMajorCategoryNum).build())
				.itemMiddleCategory(ItemMiddleCategory.builder().itemCategoryNum(itemMiddleCategoryNum).build())
				.itemSubCategory(ItemSubCategory.builder().itemCategoryNum(itemSubCategoryNum).build())
				.itemImageFile(ImageFile.builder().fileNum(itemFileNum).build())
				.build();
	}
}
