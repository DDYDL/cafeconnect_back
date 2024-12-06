package com.kong.cc.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesQuarterlyDto {

    private String menuName; // 메뉴 이름
    private Date salesDate;
    private Integer salesCount; // 수량
    private Integer quarter;

    private Integer price; // 금액
    private String menuCategoryName; // 메뉴 카테고리 이름


}