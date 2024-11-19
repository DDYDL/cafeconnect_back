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
public class Ask {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer askNum;
    private String askType;
    private String askTitle;
    private String askContent;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date askDate;
    private String askStatus;
    private String askAnswer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date askAnswerDate;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="storeCode")
    private Store storeAs;
}
