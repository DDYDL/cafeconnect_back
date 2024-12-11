package com.kong.cc.config.jwt;

public interface JwtProperties {
	String SECRET = "cafeconnect"; // 우리 서버의 고유키
//	Integer ACCESS_EXPIRATION_TIME = 60000*60*2; // 만료시간 2시간
	Integer ACCESS_EXPIRATION_TIME = 10000*1; // 10초
//	Integer REFRESH_EXPIRATION_TIME = 60000*60*24; // 24시간
	Integer REFRESH_EXPIRATION_TIME = 20000*1; // 20초
	String TOKEN_PREFIX = "Bearer "; // 앞에 붙임
	String HEADER_STRING = "Authorization";
}
