package com.kong.cc.controller;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.dto.StoreJoinDto;
import com.kong.cc.service.StoreJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreJoinController {
    private final StoreJoinService storeService;

    // 1:1 문의 작성
    // todo storeCode 넣는 부분 수정 필요
    @PostMapping("/joinStore") // AskWrite.js
    public ResponseEntity<String> joinStore(@RequestBody StoreJoinDto storeJoinDto) throws Exception {
        try {
            this.storeService.joinStore(storeJoinDto);
            return new ResponseEntity<>("가맹점 등록 완료", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("가맹점 등록 중 오류.", HttpStatus.BAD_REQUEST);

        }
    }
    // 아이디 중복 확인
    @GetMapping("/joinStore/checkDoubleId") // joinStore.js (checkDoubleId)
    public ResponseEntity<Boolean> checkDoubleId(@RequestParam String username) {
        try {
            boolean isDuplicate = storeService.checkDoubleId(username);
            return new ResponseEntity<>(isDuplicate, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 가맹점 코드 조회
    @GetMapping("/joinStore/checkStoreCode") // joinStore.js (checkDoubleId)
    public ResponseEntity<StoreDto> checkStoreCode(@RequestParam Integer storeCode) {
        try {
            StoreDto storeDto = storeService.checkStoreCode(storeCode);
            return new ResponseEntity<>(storeDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


}}
