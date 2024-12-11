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
	
	private String impUid;  //아임포트 결제 건별 발급되는 고유 식별 번호 (검증,취소,환불처리에 필수)
	
	//***참조 NullPointException 주의*** 
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
			    .itemMajorCategoryName(itemO.getItemMajorCategory() != null ? itemO.getItemMajorCategory().getItemCategoryName() : null)
			    .itemMiddleCategoryName(itemO.getItemMiddleCategory() != null ? itemO.getItemMiddleCategory().getItemCategoryName() : null)
			    .itemSubCategoryName(itemO.getItemSubCategory() != null ? itemO.getItemSubCategory().getItemCategoryName() : null)
				.itemCapacity(itemO.getItemCapacity())
				.itemUnitQuantity(itemO.getItemUnitQuantity())
				.itemUnit(itemO.getItemUnit())
				.itemStorage(itemO.getItemStorage())
				.itemFileNum(itemO.getItemImageFile()!= null ? itemO.getItemImageFile().getFileNum() : null)
				.itemFileName(itemO.getItemImageFile()!= null ? itemO.getItemImageFile().getFileName() : null)
				.orderDateStr(orderDate.toString())
				.impUid(impUid)
				.storeName(storeO.getStoreName())
				.storeAddress(storeO.getStoreAddress())
				.storeAddressNum(storeO.getStoreAddressNum())
				.storePhone(storeO.getStorePhone())
				.ownerName(storeO.getOwnerName())
				.build();
	}
}
