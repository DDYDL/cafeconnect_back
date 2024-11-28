package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesMenuDto {
    //salesAnalysis(가맹점)에서 사용
    private String menuCode;
    private String menuName;
    private Integer menuPrice;
    private Integer menuCategoryNum;

    private Integer salesCount;

    //개별 수량 합계
    private Integer salesCountSum;
    //매출 합계
    private Integer salesPriceSum;
    //전월 대비수
    private Integer compareWithCount;
    //전월 대비 금액
    private Integer compareWithPrice;
    //총 주문수량
    private Integer salesSumForMonth;
    //총 합계(월 합계 금액)
    private Integer salesPriceForMonth;
    //전월 대비
    private Integer compareWithPriceForMonth;




}
