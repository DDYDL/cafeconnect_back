package com.kong.cc.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

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
	
	
	public String makeOrderCode () {
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String uuid = UUID.randomUUID().toString().substring(0,8); //36자 문자열에서 앞 8자만 가져옴
		
		return date+"-"+uuid;
	}
	
	
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
