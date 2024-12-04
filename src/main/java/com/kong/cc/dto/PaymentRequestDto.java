package com.kong.cc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
//결제 요청 데이터를 아임포트로 전달 ,결제 관련 필드만(검증완료된) 전달 
	
	private Integer storeCode; 
	private List<Integer> cartNums;
	private Integer amount;
	
}
