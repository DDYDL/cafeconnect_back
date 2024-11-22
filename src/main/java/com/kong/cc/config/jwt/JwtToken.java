package com.kong.cc.config.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtToken {
	public String makeAccessToken(String username) {
		return JWT.create()
				.withSubject(username) // 사용자 키
				.withIssuedAt(new Date(System.currentTimeMillis())) // 만료일
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료시간
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 서명
	}

	// access token과 같은데 만료 시간만 길게
	public String makeRefreshToken(String username) {
		return JWT.create()
				.withSubject(username) // 사용자 키
				.withIssuedAt(new Date(System.currentTimeMillis())) // 만료일
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME)) // 만료시간
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 서명
	}
}
