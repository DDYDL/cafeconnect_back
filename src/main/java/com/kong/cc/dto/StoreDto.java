package com.kong.cc.dto;

import java.sql.Timestamp;
import java.sql.Date;

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
	
//	@JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화 시 필요
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화 시 필요
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss") // 원하는 형태의 LocalDateTime 설정
	private Timestamp storeOpenTime;
//	@JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화 시 필요
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화 시 필요
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss") // 원하는 형태의 LocalDateTime 설정
	private Timestamp storeCloseTime;
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
	
	// 추가
	private Integer stockCount;
	
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
				.contractPeriodStart(contractPeriodStart)
				.contractPeriodEnd(contractPeriodEnd)
				.contractDate(contractDate)
				.openingDate(openingDate)
				.storeStatus(storeStatus)
				.build();
		
		if(memberNum!=null) {
			store.setMember(Member.builder().memberNum(memberNum).build());
		}
		
		return store;
	}
}
