package com.kong.cc.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

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
}
