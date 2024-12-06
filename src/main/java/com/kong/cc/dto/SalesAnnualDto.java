package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class SalesAnnualDto {

        private Integer year;
        private String menuName; // 메뉴 이름
        private Integer salesCount; // 수량
        private Integer price; // 금액
        private String menuCategoryName;

    }