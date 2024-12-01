package com.kong.cc.entity;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

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
	private String orderCode; // 아임포트 merchant_uid
	private Integer orderCount;
	
	@CreationTimestamp
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
				.itemPrice(itemO.getItemPrice())
				.itemName(itemO.getItemName())
				.itemMajorCategoryName(itemO.getItemMajorCategory().getItemCategoryName())
				.itemMiddleCategoryName(itemO.getItemMiddleCategory().getItemCategoryName())
				.itemSubCategoryName(itemO.getItemSubCategory().getItemCategoryName())
				.itemCapacity(itemO.getItemCapacity())
				.itemUnitQuantity(itemO.getItemUnitQuantity())
				.itemUnit(itemO.getItemUnit())
				.itemStorage(itemO.getItemStorage())
				.itemFileNum(itemO.getItemImageFile().getFileNum())
				.build();
	}
}
