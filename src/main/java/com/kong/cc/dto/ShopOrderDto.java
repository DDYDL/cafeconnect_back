package com.kong.cc.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.kong.cc.entity.Item;
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
}
