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
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date noticeDate;
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mainStoreId")
	private Member memberMain;
}
