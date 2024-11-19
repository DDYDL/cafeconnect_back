package com.kong.cc.dto;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishItemDto {
	private Integer wishItemNum;
	
	private Integer storeCode;
	private String itemCode;
}
