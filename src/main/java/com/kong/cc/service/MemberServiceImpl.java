package com.kong.cc.service;

import com.kong.cc.dto.MemberDto;
import com.kong.cc.entity.Member;
import com.kong.cc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public Member join(MemberDto memberDto) throws Exception {

    	Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(encoder.encode(memberDto.getPassword()))
                .deptName(memberDto.getDeptName())
                .roles("ROLE_STORE")
                .build();

        return memberRepository.save(member);

    }

    @Override
    public String checkId(MemberDto memberDto) {
        Boolean result = memberRepository.existsByUsername(memberDto.getUsername());
        if(result == true){
            return "success";
        }else{
            return "fail";
        }



    }

}
