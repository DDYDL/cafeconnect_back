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

import org.hibernate.annotations.DynamicInsert;

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
@DynamicInsert
public class Sales {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer salesNum;

	@Temporal(TemporalType.DATE)
	private Date salesDate;
	private Integer salesCount;
	private Integer salesStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="store_code")
	private Store storeSa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="menu_code")
	private Menu menu;
}
