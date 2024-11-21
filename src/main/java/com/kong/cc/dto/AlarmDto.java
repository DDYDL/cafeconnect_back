package com.kong.cc.dto;

import java.security.Timestamp;

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
	private Timestamp alarmDate;
	private boolean alarmStatus;
	
	private Integer storeCode;
}
