package com.kong.cc.config.oauth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kong.cc.config.auth.PrincipalDetails;
import com.kong.cc.config.jwt.JwtProperties;
import com.kong.cc.config.jwt.JwtToken;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	//	private static final String URI = "/oauth2/authorization";
	// react로 바로 redirect로 넘기기
	private static final String URI = "http://localhost:3000/shopMain";
	private JwtToken jwtToken = new JwtToken();
	
	// processOAuth2User에서 return한 
	// PrincipalDetails를 파라미터로 싸서 Authentication으로 만들어줌
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		
		String accessToken = jwtToken.makeAccessToken(principalDetails.getUsername());
		String refreshToken = jwtToken.makeRefreshToken(principalDetails.getUsername());
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
		map.put("access_token", JwtProperties.TOKEN_PREFIX+accessToken);
		map.put("refresh_token", JwtProperties.TOKEN_PREFIX+refreshToken);
		
		String token = objectMapper.writeValueAsString(map); // map을 jsonString으로 바꿔줌
		System.out.println(token);
		
		
		String redirectUrl = UriComponentsBuilder.fromUriString(URI)
				.queryParam("token", token)
				.build().toUriString();
		
		response.sendRedirect(redirectUrl);
	}

}
