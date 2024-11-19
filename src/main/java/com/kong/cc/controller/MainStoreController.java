package com.kong.cc.controller;

import com.kong.cc.dto.MainStoreDto;
import com.kong.cc.entity.MainStore;
import com.kong.cc.service.MainStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainStoreController {

    private final MainStoreService mainStoreService;

    @PostMapping("/main/insert")  //JoinAccount.js
    public ResponseEntity<Object> mainInsert(MainStoreDto mainStoreDto) {
        try {
            MainStore mainStore = mainStoreService.join(mainStoreDto);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
