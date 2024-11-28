package com.kong.cc.dto;

import java.sql.Date;

import com.kong.cc.entity.Notice;
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
	
	private Integer mainStoreId;

	public Notice toEntity() {
		return Notice.builder()
				.noticeNum(this.noticeNum)
				.noticeType(this.noticeType)
				.noticeTitle(this.noticeTitle)
				.noticeContent(this.noticeContent)
				.noticeDate(this.noticeDate)
//				.mainStoreId(this.mainStoreId)
				.build();
	}

}
