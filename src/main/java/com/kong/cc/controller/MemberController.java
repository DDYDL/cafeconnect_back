package com.kong.cc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.config.auth.PrincipalDetails;
import com.kong.cc.dto.MemberDto;
import com.kong.cc.entity.Member;
import com.kong.cc.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/store") // LoginStore.js
	public ResponseEntity<MemberDto> store(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		Member member = principalDetails.getMember();
		System.out.println(member);
		return new ResponseEntity<MemberDto>(member.toDto(), HttpStatus.OK);
	}
	
//	@PostMapping("/loginMainStore") // LoginMainStore.js

    @PostMapping("/main/insert")  //JoinAccount.js
    public ResponseEntity<Object> mainInsert(@RequestBody MemberDto memberDto) {
        try {

            Member member = memberService.join(memberDto);
            if(member == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/main/checkId")
    public ResponseEntity<Object> checkId(@RequestBody MemberDto memberDto){
        try {

            String state = memberService.checkId(memberDto);

            Map<String,String> body = new HashMap<>();
            body.put("state",state);
            return new ResponseEntity<>(body,HttpStatus.OK);




        } catch (Exception e) {
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }
}
