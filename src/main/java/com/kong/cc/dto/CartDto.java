package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
	private Integer cartNum;
	private Integer cartItemCount;
	
	private Integer storeCode;
	private String itemCode;
}
