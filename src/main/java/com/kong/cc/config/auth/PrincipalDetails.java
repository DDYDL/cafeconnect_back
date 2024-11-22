package com.kong.cc.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.kong.cc.entity.Member;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private Member member;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(Member member) {
		this.member = member;
	}
	
	public PrincipalDetails(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(()->member.getRoles());
		return collect;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		if(member!=null) {
			return member.getUsername();			
		} else {
			System.out.println("==========================");
			System.out.println(attributes.get("id"));
			return String.valueOf(attributes.get("id"));
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	// OAuth2User의 함수
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	@Override
	public String getName() {
		return member.getUsername()+"";
	}

}
