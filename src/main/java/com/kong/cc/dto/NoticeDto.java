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
public class NoticeDto {
	private Integer noticeNum;
	
	private String noticeType;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeDate;
	
	private String mainStoreId;
}
