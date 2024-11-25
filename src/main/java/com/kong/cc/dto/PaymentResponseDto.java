package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
// 아임포트로 부터 응답 받을 데이터	
	private String merchantUid; //주문번호
	private String status; //결제 상태 실패,결제성공
	private int amount; //결제 금액
}
