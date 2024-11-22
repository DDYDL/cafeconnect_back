package com.kong.cc.dto;

import java.util.Date;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.Stock;
import com.kong.cc.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {
	private Integer stockNum;
	
	private Date stockReceiptDate;
	private Date stockExpirationDate;
	private Integer stockCount;
	
	private Integer storeCode;
	private String itemCode;
	
	public Stock toEntity() {
		return Stock.builder()
				.stockNum(stockNum)
				.stockReceiptDate(stockReceiptDate)
				.stockExpirationDate(stockExpirationDate)
				.stockCount(stockCount)
				.storeSt(Store.builder().storeCode(storeCode).build())
				.itemS(Item.builder().itemCode(itemCode).build())
				.build();
	}
}
