package com.kong.cc.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private Integer storeCode;

	private Date salesDate;
	private List<String> menuNameList;
	private List<Integer> salesCountList;
	private Integer salesStatus;

	private List<SalesItem>  salesData;

	private String menuCode;


}
