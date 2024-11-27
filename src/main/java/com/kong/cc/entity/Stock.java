package com.kong.cc.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kong.cc.dto.StockDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer stockNum;
	
	private Date stockReceiptDate;
	private Date stockExpirationDate;
	private Integer stockCount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storeCode")
	private Store storeSt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemCode")
	private Item itemS;
	
	public StockDto toDto() {
		return StockDto.builder()
				.stockNum(stockNum)
				.stockReceiptDate(stockReceiptDate)
				.stockExpirationDate(stockExpirationDate)
				.stockCount(stockCount)
				.storeCode(storeSt.getStoreCode())
				.itemCode(itemS.getItemCode())
				.build();
	}
}
