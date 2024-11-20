package com.kong.cc.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kong.cc.config.auth.PrincipalDetails;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	private JwtToken jwtToken = new JwtToken();

	// UsernamePasswordAuthenticationFilter(super)
	// super의 attempAuthentication 메소드가 실행되고(Authentication이 만들어짐) - UserDetails에서 받아온 user를 쌓아서 Authentication을 만듬
	// 성공하면 successfulAuthentication가 호출된다.
	// attempAuthentication 메소드가 리턴해준 Authentication을 파라미터로 받아옴
	
	// 4. 비밀번호 중간에 필터가 알아서 체크해줌 여기에서 Authentication를 가져와서 풀어논 후 user 정보를 가지고
	// 토큰 생성함. 이를 가지고 바로 프론트로 보내버림. 더이상 필터 타지 않고 그냥 프론트로 감.(일반 로그인)
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		String accessToken = jwtToken.makeAccessToken(principalDetails.getUsername());
		String refreshToken = jwtToken.makeRefreshToken(principalDetails.getUsername());
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
		map.put("access_token", JwtProperties.TOKEN_PREFIX+accessToken);
		map.put("refresh_token", JwtProperties.TOKEN_PREFIX+refreshToken);
		
		String token = objectMapper.writeValueAsString(map); // map을 jsonString으로 바꿔줌
		System.out.println(token);
		
		// 헤더에 토큰 넣어줌
		response.addHeader(JwtProperties.HEADER_STRING, token);
		response.setContentType("application/json; charset=utf-8");
	}
}
