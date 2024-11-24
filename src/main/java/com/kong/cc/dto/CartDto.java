package com.kong.cc.dto;

import com.kong.cc.entity.Cart;
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
public class CartDto {
	private Integer cartNum;
	private Integer cartItemCount;
	
	private Integer storeCode;
	private String itemCode;
	
	//cart나order에서 전체 정보까진 필요 없을 것 같아서 수정할 예정...
	private ItemDto item;  // Item toDto 사용
	private StoreDto store; // Store toDto 사용
	
	
	public Cart toEntity() {
		return Cart.builder()
				.cartNum(cartNum)
				.cartItemCount(cartItemCount)
				.storeCa(Store.builder().storeCode(storeCode).build())
				.itemCa(Item.builder().itemCode(itemCode).build())
				.build();
	}
	
}
