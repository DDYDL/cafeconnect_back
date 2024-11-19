package com.kong.cc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// origin ������ �õ��ϴ� ���� �ǹ�
		//config.addAllowedOrigin("http://localhost:3000");
		// pattern�� �ְ� �װ� �� �����
		config.addAllowedOriginPattern("*"); // ���� ���� ���
		// token�� ���� header�� ������
		config.addAllowedHeader("*"); // Access-Control-Allow-Headers �̰� �־�� ����Ʈ���� ���� ���� �� �����.
		config.addAllowedMethod("*"); // Access-Control-Allow-Method
		//config.addExposedHeader(JwtProperties.HEADER_STRING); // Ŭ���̾�Ʈ(����Ʈ ��)�� ���信 ������ �� �ִ� Header �߰�
		source.registerCorsConfiguration("/*", config); // ��Ʈ�� ���� ��� �Ϳ� config �ٿ��ش�.
		source.registerCorsConfiguration("/*/*", config);
		return new CorsFilter(source);
	}
}
