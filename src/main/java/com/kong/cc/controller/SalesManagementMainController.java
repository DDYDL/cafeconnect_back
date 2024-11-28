package com.kong.cc.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.ItemMajorCategoryForm;
import com.kong.cc.service.SalesManagementMainService;

import lombok.RequiredArgsConstructor;

//매출 관리(본사)
@RestController
@RequiredArgsConstructor
public class SalesManagementMainController {

    private final SalesManagementMainService salesManagementMainService;

    // 가맹점별 상세 주문내역
    @GetMapping("/itemRevenue/{storeCode}")
    public ResponseEntity<List<ItemMajorCategoryForm>> itemRevenue(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable Integer storeCode) throws Exception {

        // 서비스에서 결과 가져오기
        List<ItemMajorCategoryForm> result = salesManagementMainService.itemRevenue(storeCode, startDate, endDate);
//        startDate, endDate,
        return ResponseEntity.ok(result);
    }};

//        try {
//            List<ItemDto> itemDtoList = this.salesManagementMainService.itemRevenue(startDate, endDate, storeCode);
//            System.out.println("startDate: " + startDate + "endDate: " + endDate + ", storeCode: " + storeCode);
//
//            return ResponseEntity.ok(itemDtoList);
//
//        } catch (Exception e) {
//            // 예외 발생 시 빈 리스트와 오류 메시지를 반환 (혹은 오류 메시지를 포함한 ShopOrderDto 객체 반환)
//            List<ShopOrderDto> errorList = new ArrayList<>();
//            ShopOrderDto errorDto = new ShopOrderDto();
//
//            //todo 에러 메시지 띄우는 부분 처리 필요
////            errorDto.setErrorMessage("서버 오류가 발생했습니다: " + e.getMessage());
//            errorList.add(errorDto);
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Message");



