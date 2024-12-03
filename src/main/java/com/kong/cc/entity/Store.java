package com.kong.cc.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.StringUtils;

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

	 private Time storeOpenTime;
	 private Time storeCloseTime;
	 private String storeCloseDate;
	 
	 private String ownerName;
	 private String ownerPhone;
	 private String managerName;
	 private String managerPhone;
	   
	 private Date contractPeriodStart;
	 private Date contractPeriodEnd;
	 private Date contractDate;
	 private Date openingDate;
	  
	 @ColumnDefault("'active'")
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
		 	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
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
					.storeStatus(storeStatus)
					.build();
			
		 	if(storeOpenTime!=null) {
		 		storeDto.setStoreOpenTimeStr(storeOpenTime.toString().substring(0, 5));
		 	}
		 	
		 	if(storeCloseTime!=null) {
		 		storeDto.setStoreCloseTimeStr(storeCloseTime.toString().substring(0, 5));
		 	}
		 	
		 	if(contractPeriodStart!=null) {
		 		storeDto.setContractPeriodStart(fmt.format(contractPeriodStart));
		 	}
		 	if(contractPeriodEnd!=null) {
		 		storeDto.setContractPeriodEnd(fmt.format(contractPeriodEnd));
		 	}
		 	if(contractDate!=null) {
		 		storeDto.setContractDate(fmt.format(contractDate));
		 	}
		 	if(openingDate!=null) {
		 		storeDto.setOpeningDate(fmt.format(openingDate));
		 	}
		 	if(member!=null) {
				storeDto.setMemberNum(member.getMemberNum());
				storeDto.setUsername(member.getUsername());
				storeDto.setPassword(member.getPassword());
			}
		 	
			return storeDto;
		}
}
