package com.kong.cc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.config.auth.PrincipalDetails;
import com.kong.cc.dto.MemberDto;
import com.kong.cc.entity.Member;

@RestController
public class MemberController {
	
	@GetMapping("/store") // LoginStore.js
	public ResponseEntity<MemberDto> store(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		Member member = principalDetails.getMember();
		System.out.println(member);
		return new ResponseEntity<MemberDto>(member.toDto(), HttpStatus.OK);
	}
	
//	@PostMapping("/loginMainStore") // LoginMainStore.js
}
