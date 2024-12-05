package com.kong.cc.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.SalesDto;
import com.kong.cc.dto.SalesWriteDto;
import com.kong.cc.service.SalesManagementService;

import lombok.RequiredArgsConstructor;


//재무 관리 - 매출 입력(가맹점)
@RestController
@RequiredArgsConstructor
public class SalesManagementController {
    private final SalesManagementService salesManagementService;

    //매출 입력(상품명 리스트 전달)
    @GetMapping("/menuList")
    public ResponseEntity<List<MenuDto>> menuList() throws Exception {
        try {
            List<MenuDto> menuDtoList = this.salesManagementService.menuList();
            return new ResponseEntity<>(menuDtoList, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    //매출 입력
    @PostMapping("/salesWrite")
    public ResponseEntity<String> salesWrite(@RequestBody SalesWriteDto salesWriteDto) {
        System.out.println(salesWriteDto);
        try {
            salesManagementService.salesWrite(salesWriteDto.getSalesDate(),
                    salesWriteDto.getStoreCode(),
                    salesWriteDto.getSalesList());
            return ResponseEntity.ok("(controller)매출 정보가 정상적으로 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("(controller) 매출 정보 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PostMapping("/salesTemp")
    public ResponseEntity<List<SalesDto>> salesTemp(@RequestBody SalesDto salesDto) {
        try {
            List<SalesDto> salesTempList = salesManagementService.salesTemp(salesDto.getSalesDate(), salesDto.getStoreCode());
            return new ResponseEntity<List<SalesDto>>(salesTempList, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<SalesDto>>(HttpStatus.BAD_REQUEST);
        }

    }

    //매출 분석
//    @GetMapping("/salesAnalysis/{storeCode}") // SalesAnalysis.js
//    public ResponseEntity<List<SalesMenuDto>> salesAnalysis(@PathVariable Integer storeCode,
////                                                            @PathVariable String periodType,
//                                                            @RequestParam("categoryId") Integer categoryId) {
//        List<SalesMenuDto> result = salesManagementService.salesAnalysis(storeCode
////                    , periodType
//                , categoryId);
//
//        return ResponseEntity.ok(result);
//    }


}
//
//    @GetMapping("/anualSales") // SalesAnalysis.js
//    @GetMapping("/quarterlySales") // SalesAnalysis.js
//    @GetMapping("/monthlySales") // SalesAnalysis.js
//    @GetMapping("/customSales") // SalesAnalysis.js


