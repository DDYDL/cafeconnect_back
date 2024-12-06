package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesCustomDto {

    private String menuName; // 메뉴 이름
    private String menuCategoryName;
    private Integer salesCount; // 수량
    private Integer price;

}