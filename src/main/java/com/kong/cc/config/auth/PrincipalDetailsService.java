package com.kong.cc.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kong.cc.entity.Member;
import com.kong.cc.repository.MemberRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	// 1. 프론트에서 요청한 로그인이 controller가 아니라 여기로 바로 들어옴(프론트에서 보낸 파라미터 이름이 여기랑 같아야 함)
	
	// 로그인과 관련된 처리가 들어오면 UserDetailsService의 loadUserByUsername가 자동 호출
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("====================");
		System.out.println(username);
		Optional<Member> member = memberRepository.findByUsername(username);
		
		// 2. 아이디 확인 후 user의 정보를 PrincipalDetails로 싸서 보냄
		
		// 멤버가 있을 때만 member 보내줌
		// Authentication으로 만들어준다. 보내면서 필터를 타고 가다가 내가 만든 필터가 가로챔
		if(member.isPresent()) return new PrincipalDetails(member.get());
		return null;
	}

}
