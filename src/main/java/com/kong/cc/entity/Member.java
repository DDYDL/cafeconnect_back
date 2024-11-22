package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kong.cc.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberNum;
	
	private String username;
	private String password;
	
	private String deptName;
	
	private String roles;
	private String provider;
	private String providerId;
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY)
	private List<Store> storeList = new ArrayList<>();
	
	@OneToMany(mappedBy="memberMain", fetch=FetchType.LAZY)
	private List<Notice> noticeList = new ArrayList<>();
	
	public MemberDto toDto() {
		return MemberDto.builder()
				.memberNum(memberNum)
				.username(username)
				.password(password)
				.deptName(deptName)
				.roles(roles)
				.provider(provider)
				.providerId(providerId)
				.build();
	}
}
