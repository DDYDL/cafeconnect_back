package com.kong.cc.service;

import com.kong.cc.dto.MemberDto;
import com.kong.cc.entity.Member;

public interface MemberService {
	public Member join(MemberDto memberDto) throws Exception;

	String checkId(MemberDto memberDto);
}
