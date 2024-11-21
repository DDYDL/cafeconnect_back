package com.kong.cc.dto;

import lombok.Data;

@Data
public class MenuUpdateForm {

    private String menuName;
    private Integer menuPrice;
    private String menuCapacity;

    private String caffeine;
    private String calories;
    private String carbohydrate;
    private String sugar;
    private String natrium;
    private String fat;
    private String protein;
    private String menuStatus;
    private String menuCategoryName;
}
