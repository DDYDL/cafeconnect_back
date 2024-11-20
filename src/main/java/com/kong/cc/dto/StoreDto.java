package com.kong.cc.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.Ask;
import com.kong.cc.entity.Cart;
import com.kong.cc.entity.Complain;
import com.kong.cc.entity.Member;
import com.kong.cc.entity.ShopOrder;
import com.kong.cc.entity.Repair;
import com.kong.cc.entity.Stock;
import com.kong.cc.entity.Store;
import com.kong.cc.entity.WishItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
	private Integer storeCode;
	
	private String storeName;
	private String storeAddress;
	private String storeAddressNum;
	private String storePhone;
	
	private LocalTime storeOpenTime;
	private LocalTime storeCloseTime;
	private String storeCloseDate;
	
	private String ownerName;
	private String ownerPhone;
	private String managerName;
	private String managerPhone;
	
	private Date contractPeriodStart;
	private Date contractPeriodEnd;
	private Date contractDate;
	private Date openingDate;
	private String storeStatus;
	 
	private Integer memberNum;
	
	public Store toEntity() {
		Store store = Store.builder()
				.storeCode(storeCode)
				.storeName(storeName)
				.storeAddress(storeAddress)
				.storeAddressNum(storeAddressNum)
				.storePhone(storePhone)
				.storeOpenTime(storeOpenTime)
				.storeCloseTime(storeCloseTime)
				.storeCloseDate(storeCloseDate)
				.storeAddressNum(storeAddressNum)
				.ownerName(ownerName)
				.ownerPhone(ownerPhone)
				.managerName(managerName)
				.managerPhone(managerPhone)
				.contractPeriodStart(contractPeriodStart)
				.contractPeriodEnd(contractPeriodEnd)
				.contractDate(contractDate)
				.openingDate(openingDate)
				.storeStatus(storeStatus)
				.member(Member.builder().memberNum(memberNum).build())
				.build();
		return store;
	}
}
