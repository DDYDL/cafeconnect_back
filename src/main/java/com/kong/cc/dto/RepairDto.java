package com.kong.cc.dto;

import java.util.Date;

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
