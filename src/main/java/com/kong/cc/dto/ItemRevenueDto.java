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
public class ItemRevenueDto {
	private String itemCode;
	private String itemName;
	private Integer itemPrice; // 상품 금액
	private String itemCapacity; // 용량(1kg)
	private Integer itemUnitQuantity; // 1박스 당 수량
	private String itemUnit; // ea,box
	private String itemStandard; //  상품 단가
	private String itemStorage; // 상온,냉동
	private String itemCountryOrigin; // 국가

	private Integer orderCount; // 주문 수량

	private Integer itemMajorCategoryNum; //대분류 번호
	private String  itemMajorCategoryName; // 대분류 이름 (커피)
	
	private Integer itemMiddleCategoryNum; // 중분류 번호
	private String  itemMiddleCategoryName; // 중분류 이름
	
	private Integer itemSubCategoryNum; // 소분류 번호
	private String 	itemSubCategoryName; // 소분류 이름

	private String itemMajorCategoryCountSum; // 대분류 총 수량
	private String itemMajorCategoryPriceSum; // 대분류 총 금액

	private String itemMiddleCategoryCountSum; // 중분류 총 수량
	private String itemMiddleCategoryPriceSum; // 중분류 총 금액

	private String itemSubCategoryCountSum; // 소분류 총 수량
	private String itemSubCategoryPriceSum; // 소분류 총 금액


	
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
				.itemImageFile(ImageFile.builder().build())
				.build();
	}
}
