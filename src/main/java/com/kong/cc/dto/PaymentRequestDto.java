package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
//결제 요청 데이터를 아임포트로 전달 ,결제관련 필드만 전달 
	
	 	private String merchantUid; // orderCode
	    private String name;        // 주문상품
	    private int amount;         // 
	    private String buyerName;   // 가맹점 이름
	    private String buyerEmail;  // 가맹점 메일은 없으나 아임포트 기본정보라 일단 생성
	    private String buyerTel;    // 가맹점 연락처
}
