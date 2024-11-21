package com.kong.cc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuResponseDto {

    private String menuCode;

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
    private String imageUrl;
}
