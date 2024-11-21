package com.kong.cc.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

@Data
@Builder
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
}
