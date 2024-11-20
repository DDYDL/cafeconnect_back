package com.kong.cc.service;

import org.springframework.stereotype.Service;

import com.kong.cc.dto.MemberDto;
import com.kong.cc.entity.Member;
import com.kong.cc.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

}
