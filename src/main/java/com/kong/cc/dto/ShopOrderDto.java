package com.kong.cc.dto;

import java.util.Date;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.ShopOrder;
import com.kong.cc.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopOrderDto {
	private Integer orderNum;
	
	private String orderCode;
	private Integer orderCount;
	private Date orderDate;
	private String orderState;
	private String orderDelivery;
	private String orderPayment;
	
	private Integer storeCode;
	private String itemCode;
	
	public ShopOrder toEntity() {
		return ShopOrder.builder()
				.orderNum(orderNum)
				.orderCode(orderCode)
				.orderCount(orderCount)
				.orderDate(orderDate)
				.orderState(orderState)
				.orderDelivery(orderDelivery)
				.orderPayment(orderPayment)
				.storeO(Store.builder().storeCode(storeCode).build())
				.itemO(Item.builder().itemCode(itemCode).build())
				.build();
	}
}
