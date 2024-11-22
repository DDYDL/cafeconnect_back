package com.kong.cc.entity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.kong.cc.dto.StoreDto;

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
public class Store {
	 @Id
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
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="memberNum" , nullable=true)
	 private Member member;
	 
	 @OneToMany(mappedBy="storeAr", fetch=FetchType.LAZY)
	 private List<Alarm> alarmList = new ArrayList<>();
	  
	 @OneToMany(mappedBy="storeCo", fetch=FetchType.LAZY)
	 private List<Complain> complainList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeAs", fetch=FetchType.LAZY)
	 private List<Ask> askList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeR", fetch=FetchType.LAZY)
	 private List<Repair> repairList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeW", fetch=FetchType.LAZY)
	 private List<WishItem> wishItemList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeCa", fetch=FetchType.LAZY)
	 private List<Cart> cartList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeO", fetch=FetchType.LAZY)
	 private List<ShopOrder> orderList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeSt", fetch=FetchType.LAZY)
	 private List<Stock> stockList = new ArrayList<>();
	 
	 @OneToMany(mappedBy="storeSa", fetch=FetchType.LAZY)
	 private List<Sales> salesList = new ArrayList<>();
	 
	 public StoreDto toDto() {
		 	StoreDto storeDto = StoreDto.builder()
					.storeCode(storeCode)
					.storeName(storeName)
					.storeAddress(storeAddress)
					.storeAddressNum(storeAddressNum)
					.storePhone(storePhone)
					.storeOpenTime(storeOpenTime)
					.storeCloseTime(storeCloseTime)
					.storeCloseDate(storeCloseDate)
					.ownerName(ownerName)
					.ownerPhone(ownerPhone)
					.managerName(managerName)
					.managerPhone(managerPhone)
					.contractPeriodStart(contractPeriodStart)
					.contractPeriodEnd(contractPeriodEnd)
					.contractDate(contractDate)
					.openingDate(openingDate)
					.storeStatus(storeStatus)
					.build();
			
		 	if(member!=null) {
				storeDto.setMemberNum(member.getMemberNum());
			}
		 	
			return storeDto;
		}
}
