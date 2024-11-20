package com.kong.cc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.kong.cc.config.jwt.JwtAuthenticationFilter;
import com.kong.cc.config.jwt.JwtAuthrizationFilter;
import com.kong.cc.config.oauth.OAuth2SuccessHandler;
import com.kong.cc.config.oauth.PrincipalOAuth2UserService;
import com.kong.cc.repository.MemberRepository;

@Configuration // IoC(역제어, 우리가 생성한걸 spring 프레임워크가 사용할 수 있도록 제어권을 줌) 빈(bean) 등록
//이 안에 생성된걸 스프링 프레임워크에 등록한 것
@EnableWebSecurity // 필터 체인(활성화) 관리 시작 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
////config가 아니라 controller에서 권한 주고 싶을 때 사용, prePostEnabled는 권한 여러개일 때 true
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// AuthenticationManager 가지고 있는 클래스, 예전 방법

	@Autowired
	private CorsFilter cosFilter;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private OAuth2SuccessHandler oAuth2SuccessHandler;
	
	// 패스워드 암호화시켜서 저장, 생성시켜두면 spring이 자동으로 디코드한 후 패스워드 비교함
	@Bean
	public BCryptPasswordEncoder encoderPassword() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private PrincipalOAuth2UserService principalOAuth2UserService;
	
	// 필터 체인 생성
	// 내가 만든 spring security http가 필터 체인의 일부로 들어감
	// spring security가 자동으로 filter 형태로 호출시켜줌
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilter(cosFilter) // 직접 만든 필터, 다른 도메인 접근 허용
			.csrf().disable() // csrf 공격 비활성화
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // session 비활성화(세션 스토리지가 아니라 일반 세션)
		
		// 3. 필터가 가로채서 로그인 처리를 함.
		// 일반 로그인 시
		http.formLogin().disable() // 로그인 폼 비활성화(spring security의 로그인 폼 사용 안 함)
			.httpBasic().disable() // httpBasic은 header의 username, password를 암호화하지 않은 상태로 주고 받는다. 이를 사용하지 않겠다.
			.addFilterAt(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
			// UsernamePasswordAuthenticationFilter대신 new JwtAuthenticationFilter(authenticationManager) 내가 만든 이 필터를 끼워넣겠다.
		
		// OAuth2 Login
		http.oauth2Login()
			.authorizationEndpoint().baseUri("/oauth2/authorization")
			.and()
			.redirectionEndpoint().baseUri("/oauth2/callback/*") // 설정한 url
			.and()
			.userInfoEndpoint().userService(principalOAuth2UserService) // 서비스가 처리
			.and()
			.successHandler(oAuth2SuccessHandler);
			
		
		http.addFilter(new JwtAuthrizationFilter(authenticationManager(), memberRepository))
			.authorizeRequests()
			.antMatchers("/store/**").authenticated() // 로그인 필수
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
			.anyRequest().permitAll();
	}
}
