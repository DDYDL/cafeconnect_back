package com.kong.cc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.kong.cc.config.jwt.JwtProperties;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// origin 접속을 시도하는 곳을 의미
		//config.addAllowedOrigin("http://localhost:3000");
		// pattern을 주고 그걸 다 허용함
		config.addAllowedOriginPattern("*"); // 전부 접속 허용
		// token은 보통 header에 가져감
		config.addAllowedHeader("*"); // Access-Control-Allow-Headers 이게 있어야 프론트에서 뭔가 붙일 때 허용함.
		config.addAllowedMethod("*"); // Access-Control-Allow-Method
		config.addExposedHeader(JwtProperties.HEADER_STRING); // 클라이언트(리엑트 등)가 응답에 접근할 수 있는 Header 추가
		source.registerCorsConfiguration("/*", config); // 루트로 오는 모든 것에 config 붙여준다.
		source.registerCorsConfiguration("/*/*", config);
		source.registerCorsConfiguration("/*/*/*", config);
		return new CorsFilter(source);
	}
}
