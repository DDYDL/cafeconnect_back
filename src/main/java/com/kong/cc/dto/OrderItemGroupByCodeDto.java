package com.kong.cc.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemGroupByCodeDto {
	 
	 private String storeName;
	 private Integer storeCode;
	 private String orderCode;
	 private Date orderDate;
	 private String orderState;
	 private List<ShopOrderDto> orderItems; // 주문번호로가 일치하는 주문 아이템리스트 담기
	 private List<String> itemNames; // 주문번호가 일치하는 상품명만 담기 
	 private Integer totalAmount; //총 구매 금액 	
	 private Integer totalCount;  // 총 구매 아이템 수 
}
