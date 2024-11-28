package com.kong.cc.dto;

import com.kong.cc.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
	private Integer memberNum;
	
	private String username;
	private String password;
	
	private String deptName;
	
	private String roles;
	private String provider;
	private String providerId;
	
	private String fcmToken;
	private Integer storeCode;
	
	public Member toEntity() {
		return Member.builder()
				.memberNum(memberNum)
				.username(username)
				.password(password)
				.roles(roles)
				.provider(provider)
				.providerId(providerId)
				.fcmToken(fcmToken)
				.storeCode(storeCode)
				.build();
	}
}
