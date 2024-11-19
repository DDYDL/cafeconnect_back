package com.kong.cc.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class StockDto {
	private Integer stockNum;
	
	private Date stockReceiptDate;
	private Date stockExpirationDate;
	private Integer stockCount;
	
	private Integer storeCode;
	private String itemCode;
}
