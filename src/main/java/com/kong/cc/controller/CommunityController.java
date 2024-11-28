package com.kong.cc.controller;


import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.dto.SalesDto;
import com.kong.cc.entity.Ask;
import com.kong.cc.entity.Notice;
import com.kong.cc.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/noticeList/{noticeNum}") // NoticeDetail.js
    public ResponseEntity<NoticeDto> noticeDetail(@PathVariable Integer noticeNum) {
        try {

            NoticeDto noticeDto = communityService.noticeDetail(noticeNum);
            return new ResponseEntity<>(noticeDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

     //1:1 문의 리스트
    @GetMapping("/askList") // AskList.js
    public ResponseEntity<List<AskDto>> askList() {
        try {
            List<AskDto> askList = this.communityService.askList();
            return new ResponseEntity<>(askList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 1:1 문의 답글 (수정)
    @PostMapping("/askList/save/{askNum}")
    public ResponseEntity<String> saveAnswer(@PathVariable Integer askNum, @RequestBody AskDto askDto) {
        try {
                this.communityService.saveAnswer(askNum, askDto);
                return new ResponseEntity<>("답변이 저장되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("답변 저장에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 1:1 문의 답변 삭제
    @PostMapping("/askList/delete/{askNum}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Integer askNum) {
        try {
            this.communityService.deleteAnswer(askNum);
            return new ResponseEntity<>("답변이 삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("답변 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);

        }
        }

     // 1:1 문의 작성
    // todo storeCode 넣는 부분 수정 필요
    @PostMapping("/askWrite") // AskWrite.js
    public ResponseEntity<String> askWrite(@RequestBody AskDto askDto) throws Exception{
        try {
            this.communityService.askWrite(askDto);
            return new ResponseEntity<>("1:1 문의 작성 완료.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("1:1 문의 작성 중 오류.", HttpStatus.BAD_REQUEST);

        }
    }


    //컴플레인 공지 리스트
//    @GetMapping("/complainList") // ComplainList.js
//    public ResponseEntity<List<ComplainDto>> complainList(){
//        try {
//            List<ComplainDto> complainDtoList = this.communityService.complainList();
//            return new ResponseEntity<>(complainDtoList, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
    }

//    //컴플레인 상세
//    @GetMapping("/complainDetail/{complainNum}") // ComplainDetail.js
//    public ResponseEntity<Map<String,Object>> complainList(@RequestParam String storeNum){
//        try {
//            List<ComplainDto> complainList = this.complainService.complainList(storeNum);
//        }
//
//        return new ResponseEntity<List<AskDto>>((),HttpStatus.OK);
//    }
//
//    // 뒤로가기(컴플레인 상세에서)
//    @GetMapping("/backToComplainList") // ComplainList.js
//    public ResponseEntity<String> backToPage(){
//        try {
//            return new ResponseEntity<String>(HttpStatus.OK);
//        } catch(Exception e) {
//            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
//        }
//    }






