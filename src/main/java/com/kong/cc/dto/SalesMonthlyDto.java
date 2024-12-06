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
public class SalesMonthlyDto {

    private Date salesDate;
    private String menuName; // 메뉴 이름
    private Integer salesCount; // 수량
    private Integer price; // 금액
    private String menuCategoryName;
    private Integer monthValue; // 몇 월인지
}