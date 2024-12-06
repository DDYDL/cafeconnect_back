package com.kong.cc.controller;


import com.kong.cc.dto.SalesAnalysisResDto;
import com.kong.cc.dto.SalesAnnualDto;
import com.kong.cc.dto.SalesCustomDto;
import com.kong.cc.dto.SalesMonthlyDto;
import com.kong.cc.dto.SalesQuarterlyDto;
import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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


    // 매출 분석(기본 페이지)
    @GetMapping("/salesAnalysis/{storeCode}")
    public ResponseEntity<String> salesAnalysis(
            @PathVariable Integer storeCode
    ) {
        try {
            // storeCode로 특정 데이터를 조회하는 로직
            String data = "Data for store " + storeCode; // 예시 데이터 처리
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //매출 분석 (연간)
    @GetMapping("/annualAnalysis/{storeCode}") // SalesAnalysis.js
    public ResponseEntity<List<SalesAnnualDto>> annualSAnalysis(@PathVariable Integer storeCode) {
        try {
            List<SalesAnnualDto> salesTempList = salesManagementService.annualSAnalysis(storeCode);
            return new ResponseEntity<>(salesTempList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/quarterlyAnalysis/{storeCode}") // SalesAnalysis.js
    public ResponseEntity<List<SalesQuarterlyDto>> quarterlyAnalysis(@PathVariable Integer storeCode) {
        try {
            List<SalesQuarterlyDto> salesTempList = salesManagementService.quarterlyAnalysis(storeCode);
            return new ResponseEntity<>(salesTempList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/monthlyAnalysis/{storeCode}") // SalesAnalysis.js
    public ResponseEntity<List<SalesMonthlyDto>> monthlyAnalysis(@PathVariable Integer storeCode) {
        try {
            List<SalesMonthlyDto> salesTempList = salesManagementService.monthlyAnalysis(storeCode);
            return new ResponseEntity<>(salesTempList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customAnalysis/{storeCode}") // SalesAnalysis.js
        public ResponseEntity<List<SalesCustomDto>> customAnalysis(@PathVariable Integer storeCode,
                                                                   @RequestParam Date startDate,
                                                                   @RequestParam Date endDate) {
        try {
            List<SalesCustomDto> salesTempList = salesManagementService.customAnalysis(storeCode, startDate, endDate);
            return new ResponseEntity<>(salesTempList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }}






