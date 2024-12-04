package com.kong.cc.controller;


import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.service.CommunityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 커뮤니티(가맹점)
@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;


    //공지사항 리스트
    @GetMapping("/noticeList") // NoticeList.js
    public ResponseEntity<List<NoticeDto>> noticeList() {
        try {
            List<NoticeDto> noticeDtoList = communityService.noticeList();
            return new ResponseEntity<>(noticeDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 상세
    @GetMapping("/noticeDetail/{noticeNum}") // NoticeDetail.js
    public ResponseEntity<NoticeDto> noticeDetail(@PathVariable Integer noticeNum) {
        try {

            NoticeDto noticeDto = communityService.noticeDetail(noticeNum);
            return new ResponseEntity<>(noticeDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //공지사항 Modal
    @GetMapping("/noticeModal") // noticeModal.js
    public ResponseEntity<List<NoticeDto>> noticeModal() {
        try {
            List<NoticeDto> noticeDtoList = communityService.noticeModal();
            return new ResponseEntity<>(noticeDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //1:1 문의 리스트
    @GetMapping("/askListStore/{storeCode}") // AskList.js
    public ResponseEntity<List<AskDto>> askList(@PathVariable Integer storeCode) {
        try {
            List<AskDto> askList = this.communityService.askList(storeCode);
            return new ResponseEntity<>(askList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/askDetailStore/{storeCode}/getAnswer/{askNum}") // AskList.js
    public ResponseEntity<AskDto> askAnswer(@PathVariable Integer storeCode, @PathVariable Integer askNum) {
        try {
            AskDto askAnswer = this.communityService.askAnswer(storeCode, askNum);
            return new ResponseEntity<>(askAnswer, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 1:1 문의 작성
    // todo storeCode 넣는 부분 수정 필요
    @PostMapping("/askWrite") // AskWrite.js
    public ResponseEntity<String> askWrite(@RequestBody AskDto askDto) throws Exception {
        try {
            this.communityService.askWrite(askDto);
            return new ResponseEntity<>("1:1 문의 작성 완료.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("1:1 문의 작성 중 오류.", HttpStatus.BAD_REQUEST);

        }
    }

    //컴플레인 공지 리스트
    @GetMapping("/complainListStore/{storeCode}") // ComplainList.js
    public ResponseEntity<List<ComplainDto>> complainList(@PathVariable Integer storeCode) {
        try {
            List<ComplainDto> complainDtoList = this.communityService.complainList(storeCode);
            return new ResponseEntity<>(complainDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //    //컴플레인 상세
    @GetMapping("/complainDetailStore/{complainNum}") // ComplainDetail.js
    public ResponseEntity<ComplainDto> complainDetail(@PathVariable Integer complainNum) {
        try {
            ComplainDto complainDto = this.communityService.complainDetail(complainNum);
            return new ResponseEntity<>(complainDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }};






