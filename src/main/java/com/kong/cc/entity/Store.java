package com.kong.cc.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
	   
	 @JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화 시 필요
	 @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화 시 필요
	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss") // 원하는 형태의 LocalDateTime 설정
	 private LocalDateTime storeOpenTime;
	 @JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화 시 필요
	 @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화 시 필요
	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss") // 원하는 형태의 LocalDateTime 설정
	 private LocalDateTime storeCloseTime;
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
