package com.kong.cc.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kong.cc.entity.Repair;
import org.hibernate.annotations.CreationTimestamp;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairDto {
	private Integer repairNum;
	
	private String repairType;
	private String repairTitle;
	private String repairContent;
	private Date repairDate;
	private String repairStatus;
	private String repairAnswer;
	private Date repairAnswerDate;
	
    private Integer storeCode;
    private String itemCode;



}
