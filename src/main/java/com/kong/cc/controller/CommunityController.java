package com.kong.cc.controller;


import org.springframework.web.bind.annotation.RestController;

// 커뮤니티(가맹점)
@RestController
public class CommunityController {

//    //공지사항 리스트
//    @GetMapping("/noticeList") // NoticeList.js
//    public List<NoticeDto> noticeList(){
//        return this.NoticeService.noticeList();
//    }
//
//    // 공지사항 세부
//    @GetMapping("/noticeDetail/{noticeNum}") // NoticeDetail.js
//
//    // 1:1 문의 작성
//    @PostMapping("/askWrite") // AskWrite.js
//    public ResponseEntity<Map<String, Object>> askWrite(@RequestBody AskDto body) throws Exception{
//        Map<String, Object> response = new HashMap<>();
//        try {
//            // 서비스 호출
//            this.askService.askWrite(body);
//            // 성공 응답 생성
//            response.put("success", true);
//            response.put("message", "문의 작성이 성공적으로 완료되었습니다.");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            // 실패 응답 생성
//            response.put("success", false);
//            response.put("message", "문의 작성 중 오류가 발생했습니다.");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    //1:1 문의 리스트
//    @GetMapping("/askList") // AskList.js
//    public ResponseEntity<Map<String,Object>> askList(@RequestParam ){
//        try {
//            List<AskDto> askList = this.askService.askList();
//            return new ResponseEntity<List<AskDto>>((),HttpStatus.OK);
//        } catch (Exception e){
//
//        }
//    }
//
//    //컴플레인 공지 리스트
//    @GetMapping("/complainList") // ComplainList.js
//    public ResponseEntity<Map<String,Object>> complainList(){
//        try {
//            List<ComplainDto> complainList = this.complainService.complainList();
//            return new ResponseEntity<List<AskDto>>((),HttpStatus.OK);
//        } catch (Exception e){
//
//            return new ResponseEntity<List<AskDto>>((),HttpStatus.BAD_REQUEST);
//        }
//    }
//
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



}


