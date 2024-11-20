package com.kong.cc.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.kong.cc.entity.Member;
import com.kong.cc.entity.Store;

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
	
	private String roles;
	private String provider;
	private String providerId;
	
	public Member toEntity() {
		return Member.builder()
				.memberNum(memberNum)
				.username(username)
				.password(password)
				.roles(roles)
				.provider(provider)
				.providerId(providerId)
				.build();
	}
}
