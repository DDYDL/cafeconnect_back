package com.kong.cc.dto;

import lombok.Data;

@Data
public class ItemUpdateForm {

    private String itemName;
    private Integer itemPrice;
    private String itemCapacity;
    private Integer itemUnitQuantity;
    private String itemUnit;
    private String itemStandard;
    private String itemStorage;
    private String itemCountryOrigin;
    private String itemCategoryMajorName;
    private String itemCategoryMiddleName;
    private String itemCategorySubName;


}
