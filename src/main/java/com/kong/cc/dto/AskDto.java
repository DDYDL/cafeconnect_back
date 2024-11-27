package com.kong.cc.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AskDto {
	private Integer askNum;
	
    private String askType;
    private String askTitle;
    private String askContent;
    private Date askDate;
    private String askStatus;
    private String askAnswer;
    private Date askAnswerDate;
    
    private Integer storeCode;
}
