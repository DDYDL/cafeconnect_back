package com.kong.cc.dto;

import com.kong.cc.entity.ImageFile;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.entity.ItemMiddleCategory;
import com.kong.cc.entity.ItemSubCategory;

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


	private Integer wishItemNum;
	
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
