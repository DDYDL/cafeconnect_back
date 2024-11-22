package com.kong.cc.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
public class ItemResponseDto {

    @Id
    private String itemCode;

    private String itemName;
    private Integer itemPrice;
    private String itemCapacity;
    private Integer itemUnitQuantity;
    private String itemUnit;
    private String itemStandard;
    private String itemStorage;
    private String itemCountryOrigin;
    private String itemMajorCategoryName;
    private String itemMiddleCategoryName;
    private String itemSubCategoryName;
    private String imageUrl;


    @QueryProjection
    public ItemResponseDto(String imageUrl, String itemCapacity, String itemCode, String itemCountryOrigin, String itemMajorCategoryName, String itemMiddleCategoryName, String itemName, Integer itemPrice, String itemStandard, String itemStorage, String itemSubCategoryName, String itemUnit, Integer itemUnitQuantity) {
        this.imageUrl = imageUrl;
        this.itemCapacity = itemCapacity;
        this.itemCode = itemCode;
        this.itemCountryOrigin = itemCountryOrigin;
        this.itemMajorCategoryName = itemMajorCategoryName;
        this.itemMiddleCategoryName = itemMiddleCategoryName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStandard = itemStandard;
        this.itemStorage = itemStorage;
        this.itemSubCategoryName = itemSubCategoryName;
        this.itemUnit = itemUnit;
        this.itemUnitQuantity = itemUnitQuantity;
    }
}
