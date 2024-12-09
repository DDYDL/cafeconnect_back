package com.kong.cc.dto;

import java.math.BigDecimal;
import java.util.Date;
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
	private String merchantUid; // 주문번호 
	private String status; //결제 상태 실패,결제성공
	private Integer totalAmount; //결제 금액

	private List<Integer> cartNums; //결제된 장바구니 
	private Integer storeCode;
	private String paymentMethod;
	

	//취소처리 후 아임포트 SDK에서 제공하는 Payment객체를 통해 결제 정보를 받아옴 
    //취소 응답 발을 추가 데이터 
    private String paymentStatus; // 결제상태
	private Date cancelDate;       // 취소일
    private Integer cancelAmount;  // 취소 금액 
    private String cancelReason; //취소 사유
    private String cardName;       // 결제 카드명
    private String cardNumber;     // 카드 번호
	private String bankName;	   // 은행명
	private String receiptUrl; //거레 영수증 
}
