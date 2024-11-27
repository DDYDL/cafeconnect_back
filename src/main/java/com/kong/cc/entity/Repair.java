package com.kong.cc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.kong.cc.dto.RepairDto;
import com.kong.cc.dto.RepairResponseDto;

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
public class Repair {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer repairNum;
	private String repairType;
	private String repairTitle;
	private String repairContent;
	
	@CreationTimestamp
	private Date repairDate;
	private String repairStatus;
	private String repairAnswer;
	@CreationTimestamp
	private Date repairAnswerDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="storeCode")
    private Store storeR;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="itemCode")
    private Item itemR;
	
	public RepairResponseDto toDto() {
	
		return  RepairResponseDto.builder()
				.repairNum(repairNum)
				.repairType(repairType)
				.repairTitle(repairTitle)
				.repairContent(repairContent)
				.repairDate(repairDate)
				.repairStatus(repairStatus)
				.repairAnswer(repairAnswer)
				.repairAnswerDate(repairAnswerDate)
				.storeCode(storeR !=null?storeR.getStoreCode():null)
				.storeName(storeR !=null?storeR.getStoreName():null)
				.itemCode(itemR !=null?itemR.getItemCode():null)
				.build();
	}
}
