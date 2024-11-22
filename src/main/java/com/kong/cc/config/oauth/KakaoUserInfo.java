package com.kong.cc.config.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes;
	private Map<String,Object> properties;

	public KakaoUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
		properties = (Map<String,Object>)attributes.get("properties");
	}
	
	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getProvider() {
		return "Kakao";
	}
	
	@Override
	public String getEmail() {
		return (String)properties.get("email");
	}

	@Override
	public String getName() {
		return String.valueOf(attributes.get("profile_nickname"));
	}

	@Override
	public String getUsername() {
		return String.valueOf(attributes.get("id"));
	}
}
