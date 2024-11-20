package com.kong.cc.dto;

import java.util.Date;

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
}
