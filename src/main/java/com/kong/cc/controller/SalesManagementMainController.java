package com.kong.cc.controller;

import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.service.SalesManagementMainService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

//매출 관리(본사)
@RestController
@RequiredArgsConstructor
public class SalesManagementMainController {

 private SalesManagementMainService salesManagementMainService;

    // 가맹점별 상세주문내역
    @GetMapping("/itemRevenue/{storeCode}")
    public ResponseEntity<List<ShopOrderDto>> itemRevenue(@RequestParam Date startDate, @RequestParam Date endDate, @PathVariable Integer storeCode) throws Exception {

        System.out.println("startDate: " + startDate + "endDate: " + endDate + ", storeCode: " + storeCode);

        try {
            // 정상 처리: 서비스에서 반환된 데이터를 그대로 응답 본문에 담아 반환
            List<ShopOrderDto> shopOrderDtoList = this.salesManagementMainService.itemRevenue(startDate, endDate, storeCode);
            System.out.println("startDate: " + startDate + "endDate: " + endDate + ", storeCode: " + storeCode);

            return ResponseEntity.ok(shopOrderDtoList); // HTTP 200 OK 상태와 데이터를 반환

        } catch (Exception e) {
            // 예외 발생 시 빈 리스트와 오류 메시지를 반환 (혹은 오류 메시지를 포함한 ShopOrderDto 객체 반환)
            List<ShopOrderDto> errorList = new ArrayList<>();
            ShopOrderDto errorDto = new ShopOrderDto(); // 가정: ShopOrderDto에 메시지를 담을 필드가 있다고 가정

            //todo 에러 메시지 띄우는 부분 처리 필요
//            errorDto.setErrorMessage("서버 오류가 발생했습니다: " + e.getMessage());
            errorList.add(errorDto);

            return ResponseEntity.status(500).body(errorList); // 빈 리스트 또는 오류 정보를 담은 객체 반환
        }
    }
}
