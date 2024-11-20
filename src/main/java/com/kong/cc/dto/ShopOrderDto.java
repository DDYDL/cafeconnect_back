package com.kong.cc.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.querydsl.core.annotations.QueryProjection;
import org.hibernate.annotations.CreationTimestamp;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ShopOrderDto {
    private Integer orderNum;

    private String orderCode;
    private Integer orderCount;
    private LocalDate orderDate;
    private String orderState;
    private String orderDelivery;
    private String orderPayment;

    private Integer storeCode;
    private String itemCode;

    @QueryProjection
    public ShopOrderDto(Integer orderNum, String orderCode, Integer orderCount, LocalDate orderDate, String orderState, String orderDelivery, String orderPayment, Integer storeCode, String itemCode) {
        this.orderNum = orderNum;
        this.orderCode = orderCode;
        this.orderCount = orderCount;
        this.orderDate = orderDate;
        this.orderState = orderState;
        this.orderDelivery = orderDelivery;
        this.orderPayment = orderPayment;
        this.storeCode = storeCode;
        this.itemCode = itemCode;
    }
}
