package com.kong.cc.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.kong.cc.config.auth.PrincipalDetails;
import com.kong.cc.entity.Member;
import com.kong.cc.repository.MemberRepository;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private MemberRepository memberRepository;

	// 최종 사용자 정보를 userRequest로 받아옴, service 호출 시 자동으로 loadUser함수 호출
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println(userRequest.getClientRegistration());
		System.out.println(userRequest.getAccessToken());
		System.out.println(userRequest.getAdditionalParameters());
		
		OAuth2User oAuth2User = super.loadUser(userRequest); // 토큰, 사용자 정보 등 여러가지 가지고 있음
		System.out.println(oAuth2User);
		System.out.println(oAuth2User.getAttributes());
		return processOAuth2User(userRequest, oAuth2User);
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = null;
		
		// Naver인지 Kakao인지 확인
		if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			System.out.println("카카오 로그인");
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인");
			// 네이버는 response 속성 안에 정보가 들어가 있음
			oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttribute("response"));
		} else {
			System.out.println("카카오와 네이버만 지원합니다.");
		}
		
		// 1. DB에서 조회
		Member member = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		System.out.println(member);
		// 1-1. 이미 가입되어 있으면 정보 수정
		if(member!=null) {
			member.setUsername(oAuth2UserInfo.getEmail());
			memberRepository.save(member);
		} else { // 1-2. 가입되어 있지 않으면 삽입
			Member nMember = Member.builder()
							// 소셜 로그인 시 username을 이메일로 설정
							.username(oAuth2UserInfo.getEmail())
							.roles("ROLE_STORE")
							.provider(oAuth2UserInfo.getProvider())
							.providerId(oAuth2UserInfo.getProviderId())
							.build();
			memberRepository.save(nMember);
		}
		return new PrincipalDetails(member, oAuth2User.getAttributes());
	}	
}
