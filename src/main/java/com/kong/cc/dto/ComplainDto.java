package com.kong.cc.dto;

import java.sql.Date;

import com.kong.cc.entity.Complain;
import com.kong.cc.entity.Store;

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
    
    public Complain toEntity() {
    	return Complain.builder()
    			.complainNum(complainNum)
    			.userName(userName)
    			.userPhone(userPhone)
    			.complainTitle(complainTitle)
    			.complainContent(complainContent)
    			.complainDate(complainDate)
    			.complainStatus(complainStatus)
    			.complainAnswer(complainAnswer)
    			.complainAnswerDate(complainAnswerDate)
    			.storeCo(Store.builder().storeCode(storeCode).build())
    			.build();
    }
}
