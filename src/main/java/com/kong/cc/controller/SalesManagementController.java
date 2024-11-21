package com.kong.cc.controller;

import com.kong.cc.dto.SalesDto;
import com.kong.cc.service.SalesManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

//재무 관리 - 매출 입력(가맹점)
@RestController("/")
@RequiredArgsConstructor
public class SalesManagementController {
    private final SalesManagementService salesManagementService;

    //매출 입력
    @PostMapping("/salesWrite")
    public ResponseEntity<String> salesWrite(@RequestBody SalesDto body
//            , @RequestParam("storeCode") Integer storeCode
    ) throws Exception {
//        List<Integer> storeCodeList = body.stream().map(SalesDto::getStoreCode).collect(Collectors.toList());
        try {
            this.salesManagementService.salesWrite(body);
            return ResponseEntity.ok("(controller)매출 정보가 정상적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("(controller) 매출 정보 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}

//매출 분석
//    @GetMapping("/salesAnalysis") // SalesAnalysis.js
//    public ResponseEntity<SalesDto> salesAnalysis(@RequestParam("period") String period){
//        return "";
//    }
//
//    @GetMapping("/anualSales") // SalesAnalysis.js
//    @GetMapping("/quarterlySales") // SalesAnalysis.js
//    @GetMapping("/monthlySales") // SalesAnalysis.js
//    @GetMapping("/customSales") // SalesAnalysis.js


