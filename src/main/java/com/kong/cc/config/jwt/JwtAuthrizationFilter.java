package com.kong.cc.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kong.cc.config.auth.PrincipalDetails;
import com.kong.cc.entity.Member;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.MemberRepository;

// 인가: 로그인 처리가 되어야만 하는 처리가 들어왔을 때 실행
public class JwtAuthrizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AlarmDslRepository alarmDslRepository;

	private JwtToken jwtToken = new JwtToken();

	public JwtAuthrizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();

		
		System.out.println("================================================================");
		System.out.println(uri);
		// 로그인(인증)이 필요없는 요청은 그대로 진행
		// store나 mainstore가 아니면
		if(uri.contains("/selectCategory") || uri.contains("/selectMenuCategory") || uri.contains("/selectMenu") || uri.contains("/fcmToken")) {
			chain.doFilter(request, response);
			System.out.println("############");
			return;
		}

		String authentication = request.getHeader(JwtProperties.HEADER_STRING);
		if(authentication==null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요1"); // 인증 오류가 난걸 알려줌
			System.out.println("1");
			return;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,String> token = objectMapper.readValue(authentication, Map.class);
		System.out.println(token);

		// accessToken validate check : header로부터 accessToken 가져와 bear check
		String accessToken = token.get("access_token");
		if(!accessToken.startsWith(JwtProperties.TOKEN_PREFIX)) { // 토큰 맨 앞에 무언가 붙어있지 않다면
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요2");
			System.out.println("2");
			return;
		}

		accessToken = accessToken.replace(JwtProperties.TOKEN_PREFIX, ""); // 앞에 붙은 bearer 떼기
		
		System.out.println(accessToken);
		
		try {
			// 1. Access Token check
			// 1-1. 보안키, 만료시간 check
			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)) // 만들어둔 비밀키 필요
				.build()
				.verify(accessToken) // 만료시간 지났는지 체크
				.getClaim("sub")
				.asString();
			System.out.println("3. token ------------");
			System.out.println(username);

			System.out.println("3");
			// 1-2. username check
			if(username==null || username.equals("")) throw new Exception("로그인 필요3"); // 사용자가 없을 때
			
			Optional<Member> member = memberRepository.findByUsername(username);
			if(member.get()==null) throw new Exception("로그인 필요4"); // 사용자가 DB에 없을 때

			// roles와 url이 맞는지 체크, 안 맞으면 권한 에러 나도록
			// 여기까지 왔으면 로그인 인증 성공(user 있음)

			// 1-3. User를 Authentication로 생성하여 Security Session에 넣어준다.(그러면 Controller에서 사용할 수 있다.)
			PrincipalDetails principalDetails = new PrincipalDetails(member.get());
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principalDetails,
					null, principalDetails.getAuthorities()); // user를 principalDetails로 싸아놓고, 권한도 줘서
			SecurityContextHolder.getContext().setAuthentication(auth); // Authentication으로 만듬

			System.out.println("4. token ============");
			System.out.println(member.get().getStoreCode());
			
			chain.doFilter(request, response);
			return;
		} catch(Exception e) {
			e.printStackTrace();

			try {
				// 2. Refresh Token check : Access Token invalidate일 경우
				String refreshToken = token.get("refresh_token");
				if(!refreshToken.startsWith(JwtProperties.TOKEN_PREFIX)) { // 토큰 맨 앞에 무언가 붙어있다면
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요5");
					System.out.println("5");
					return;
				}
				refreshToken = refreshToken.replace(JwtProperties.TOKEN_PREFIX, "");
				// 2-1. 보안키, 만료시간 check
				String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)) // 만들어둔 비밀키 필요
						.build()
						.verify(refreshToken) // 만료시간 지났는지 체크
						.getClaim("sub")
						.asString();

				// 2-2. username check
				System.out.println(username);
				if(username==null || username.equals("")) throw new Exception("로그인 필요6"); // 사용자가 없을 때
				Optional<Member> member = memberRepository.findByUsername(username);
				if(member.isEmpty()) {
					System.out.println(username);
					System.out.println(member);
					throw new Exception("로그인 필요7"); // 사용자가 DB에 없을 때	
				}


				// accessToken, refreshToken 다시 만들어 보낸다.
				String reAccessToken = jwtToken.makeAccessToken(username);
				String reRefreshToken = jwtToken.makeRefreshToken(username);
				Map<String, String> map = new HashMap<>();
				map.put("access_token", JwtProperties.TOKEN_PREFIX+reAccessToken);
				map.put("refresh_token", JwtProperties.TOKEN_PREFIX+reRefreshToken);
				String reToken = objectMapper.writeValueAsString(map); //map->jsonString
				
				System.out.println("1");
				System.out.println("1");
				
				PrincipalDetails principalDetails = new PrincipalDetails(member.get());
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principalDetails,
						null, principalDetails.getAuthorities()); // user를 principalDetails로 싸아놓고, 권한도 줘서
				SecurityContextHolder.getContext().setAuthentication(auth); // Authentication으로 만듬
				
				response.addHeader(JwtProperties.HEADER_STRING, reToken);
				response.setContentType("application/json; charset=utf-8");
//				response.getWriter().print("token"); // token 다시 준 것임을 body에 알려줌
				chain.doFilter(request, response);

			} catch(Exception e2) {
				e2.printStackTrace();
				// accessToken, refreshToken 둘 다 인증 실패, 재로그인 필요
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요8");
			}
		}
	}
}
