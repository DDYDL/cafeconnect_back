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

import com.kong.cc.dto.NoticeDto;

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
public class Notice {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeNum;
    private String noticeType;
    private String noticeTitle;
    private String noticeContent;
    
    @CreationTimestamp
    private Date noticeDate;
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mainStoreId")
	private Member memberMain;

    public NoticeDto toDto() {
    	return NoticeDto.builder()
    			.noticeNum(noticeNum)
    			.noticeType(noticeType)
    			.noticeTitle(noticeTitle)
    			.noticeContent(noticeContent)
    			.noticeDate(noticeDate)
//    			.mainStoreId(memberMain.getMemberNum())
    			.build();
    }
}
