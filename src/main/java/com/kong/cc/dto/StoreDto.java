package com.kong.cc.dto;

import java.sql.Date;
import java.sql.Time;

import com.kong.cc.entity.Member;
import com.kong.cc.entity.Store;

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
	
	private Time storeOpenTime;
	private Time storeCloseTime;
	private String storeCloseDate;
	
	private String ownerName;
	private String ownerPhone;
	private String managerName;
	private String managerPhone;
	
	private String contractPeriodStart;
	private String contractPeriodEnd;
	private String contractDate;
	private String openingDate;
	private String storeStatus;
	 
	private Integer memberNum;
	
	// 추가
	private Integer stockCount;
	private String storeOpenTimeStr;
	private String storeCloseTimeStr;
	
	private String username;
	private String password;
	
	public Store toEntity() {
		Store store = Store.builder()
				.storeCode(storeCode)
				.storeName(storeName)
				.storeAddress(storeAddress)
				.storeAddressNum(storeAddressNum)
				.storeCode(storeCode)
				.storeName(storeName)
				.storeAddress(storeAddress)
				.storePhone(storePhone)
				.storeOpenTime(storeOpenTime)
				.storeCloseTime(storeCloseTime)
				.storeCloseDate(storeCloseDate)
				.ownerName(ownerName)
				.ownerPhone(ownerPhone)
				.managerName(managerName)
				.managerPhone(managerPhone)
				.storeStatus(storeStatus)
				.build();
		
	 	if(contractPeriodStart!=null) {
	 		store.setContractPeriodStart(Date.valueOf(contractPeriodStart));
	 	}
	 	if(contractPeriodEnd!=null) {
	 		store.setContractPeriodEnd(Date.valueOf(contractPeriodEnd));
	 	}
	 	if(contractDate!=null) {
	 		store.setContractDate(Date.valueOf(contractDate));
	 	}
	 	if(openingDate!=null) {
	 		store.setOpeningDate(Date.valueOf(openingDate));
	 	}
		if(memberNum!=null) {
			store.setMember(Member.builder().memberNum(memberNum).build());
		}
		
		return store;
	}
}
