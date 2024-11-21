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
public class SalesDto {
	private Integer salesNum; 

	private Date salesDate;
	private Integer salesCount;
	private Integer salesStatus;
	
	private Integer storeCode;
	private String menuCode;
}
