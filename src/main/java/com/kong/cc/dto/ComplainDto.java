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
public class ComplainDto {
	private Integer complainNum;
	
    private String userName;
    private String userPhone;
    private String complainTitle;
    private String complainContent;
    private Date complainDate;
    private Boolean complainStatus;
    private String complainAnswer;
    private Date complainAnswerDate;
    
    private Integer storeCode;
}
