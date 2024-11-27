package com.kong.cc.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.kong.cc.dto.AlarmDto;

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
public class Alarm {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer alarmNum;
	
	private String alarmType;
	private String alarmContent;
	
	@CreationTimestamp
	private Date alarmDate;
	
	private boolean alarmStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storeCode")
	private Store storeAr;
	
	public AlarmDto toDto() {
		return AlarmDto.builder()
				.alarmNum(alarmNum)
				.alarmType(alarmType)
				.alarmContent(alarmContent)
				.alarmDate(alarmDate)
				.alarmStatus(alarmStatus)
				.storeCode(storeAr.getStoreCode())
				.build();
	}
}
