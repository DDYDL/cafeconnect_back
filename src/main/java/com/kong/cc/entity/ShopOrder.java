package com.kong.cc.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.*;

import com.kong.cc.dto.ShopOrderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ShopOrder {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderNum;
	private String orderCode;
	private Integer orderCount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	private String orderState;
	private String orderDelivery;
	private String orderPayment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storeCode")
	private Store storeO;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemCode")
	private Item itemO;
	
	public ShopOrderDto toDto() {
		return ShopOrderDto.builder()
				.orderNum(orderNum)
				.orderCode(orderCode)
				.orderCount(orderCount)
				.orderDate(orderDate)
				.orderState(orderState)
				.orderDelivery(orderDelivery)
				.orderPayment(orderPayment)
				.storeCode(storeO.getStoreCode())
				.itemCode(itemO.getItemCode())
				.build();
	}
}
