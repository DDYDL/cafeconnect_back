package com.kong.cc.entity;

import java.sql.Date;

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

import com.kong.cc.dto.ComplainDto;

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
public class Complain {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complainNum;
    private String userName;
    private String userPhone;
    private String complainTitle;
    private String complainContent;
    
    @CreationTimestamp
    private Date complainDate;
    private Boolean complainStatus;
    private String complainAnswer;
    @CreationTimestamp
    private Date complainAnswerDate;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="storeCode")
    private Store storeCo;
    
    public ComplainDto toDto() {
    	return ComplainDto.builder()
    			.complainNum(complainNum)
    			.userName(userName)
    			.userPhone(userPhone)
    			.complainTitle(complainTitle)
    			.complainContent(complainContent)
    			.complainDate(complainDate)
    			.complainStatus(complainStatus)
    			.complainAnswer(complainAnswer)
    			.complainAnswerDate(complainAnswerDate)
    			.storeCode(storeCo.getStoreCode())
    			.build();
    }
}
