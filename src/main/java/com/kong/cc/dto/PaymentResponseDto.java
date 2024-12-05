package com.kong.cc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
	// 아임포트로 부터 응답 받을 데이터(결제 정보를 담음)
	private String impUid;
	private String merchantUid; // 
	private String status; //결제 상태 실패,결제성공
	private int totalAmount; //결제 금액

	private List<Integer> cartNums; //결제된 장바구니 
	private Integer storeCode;
	private String paymentMethod; 
}
