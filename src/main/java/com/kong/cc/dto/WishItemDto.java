package com.kong.cc.dto;

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
