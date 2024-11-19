package com.kong.cc.dto;

import java.security.Timestamp;
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
