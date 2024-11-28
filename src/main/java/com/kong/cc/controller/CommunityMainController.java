package com.kong.cc.controller;

import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.service.CommunityMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityMainController {

    private final CommunityMainService communityMainService;
	
//  @GetMapping("/askListMain")  	 	// AskListMain.js
//  @GetMapping("/askDetailMain") 	 	// AskDetailMain.js
//  @PostMapping("/askAnswer") 			// AskDetailMain.js
//  @PostMapping("/complainListMain")   // ComplainListMain.js
//  @GetMapping("/complainDetailMain")  // ComplainDetailMain.js
//  @PostMapping("/complainAnswer") 	// ComplainDetailMain.js
//
//  @PostMapping("/writeNotice") 		// WriteNotice.js
//  @GetMapping("/modifyNotice") 		// ModifyNotice.js
//  @PostMapping("/modifyNotice") 		// ModifyNotice.js

    //공지사항 리스트 (본사)
    @GetMapping("/noticeListMain") // NoticeListMain.js
    public ResponseEntity<List<NoticeDto>> noticeListMain() {
        try {
            List<NoticeDto> noticeMainDtoList = communityMainService.noticeListMain();
            return new ResponseEntity<>(noticeMainDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 상세 (본사)
    @GetMapping("/noticeDetailMain/{noticeNum}") // NoticeMainDetail.js
    public ResponseEntity<NoticeDto> noticeDetailMain(@PathVariable Integer noticeNum) {
        try {
            NoticeDto noticeMainDto = communityMainService.noticeDetailMain(noticeNum);
            return new ResponseEntity<>(noticeMainDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 작성 (본사)
    @PostMapping("/noticeWriteMain")
    public ResponseEntity<String> noticeWriteMain(@RequestBody NoticeDto noticeDto) {
        try {
            System.out.println("notice작성 성공");
            this.communityMainService.noticeWriteMain(noticeDto);
            return new ResponseEntity<>("답변이 저장되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("notice작성 실패");
            e.printStackTrace();
            return new ResponseEntity<>("답변 저장에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
