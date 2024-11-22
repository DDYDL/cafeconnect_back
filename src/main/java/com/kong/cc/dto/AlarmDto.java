package com.kong.cc.dto;

import java.util.Date;

import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmDto {
	private Integer alarmNum;
	
	private String alarmType;
	private String alarmContent;
	private Date alarmDate;
	private boolean alarmStatus;
	
	private Integer storeCode;
	
	public Alarm toEntity() {
		return Alarm.builder()
				.alarmNum(alarmNum)
				.alarmType(alarmType)
				.alarmContent(alarmContent)
				.alarmDate(alarmDate)
				.alarmStatus(alarmStatus)
				.storeAr(Store.builder().storeCode(storeCode).build())
				.build();
	}
}
