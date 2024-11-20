package com.kong.cc.service;

import com.kong.cc.dto.QShopOrderDto;
import com.kong.cc.dto.SalesDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.core.types.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesManagementMainServiceImpl implements SalesManagementMainService {

    private final ShopOrderRepository shopOrderRepository;
    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;
    @Override
    public List<ShopOrderDto> itemRevenue(LocalDate orderDate, Integer storeCode) {

        // 로그 추가: 받은 파라미터 확인
        System.out.println("Received orderDate: " + orderDate + ", storeCode: " + storeCode);

        System.out.println("No orders found for the given date and store code");
        // todo 필요 정보 (가맹점 코드, 매출 기간, 상품 정보, 총 금액)
        // 1) Order 테이블에서 storeCode와 orderDate가 선택한 storeCode인것만 가져오기
        // 2) 해당 storeCode들의 item정보(itemCode로 item테이블에서 조회) 가져오기

        // Store 객체를 storeCode로 조회
        Store store = this.storeRepository.findById(storeCode)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // orderDate, storeCode 일치하는 주문 내역 가져오기
        List<ShopOrder> shopOrderList = this.shopOrderRepository.findByOrderDateAndStoreO(orderDate, store);
        if (shopOrderList.isEmpty()) {
            throw new RuntimeException("No orders found for the given date and store");
        }


        // 위 주문 내역 중, itemCode 가져오기
        List<Item> itemCodeListOb = shopOrderList.stream().map(ShopOrder::getItemO).collect(Collectors.toList());
        //-- itemCodeList는 어떤 형태로 되어있는지? integer가 아니라 객체 형태로 되어있는듯
        System.out.println("itemCodeListOb = " + itemCodeListOb);
        // itemCodeList (String형식)
        List<String> itemCodeList = itemCodeListOb.stream().map(Item::getItemCode).collect(Collectors.toList());

        // 빈 객체 생성
        List<Item> itemInfo = new ArrayList<>();  // 결과를 저장할 리스트

        for (String itemCode : itemCodeList) {
            if (!itemCode.isEmpty()) {
                List<Item> item = this.itemRepository.findByItemCode(itemCode);

                itemInfo.addAll(item);
            }

        }

        List<ShopOrderDto> shopOrderDtoList = new ArrayList<>();

        for (ShopOrder order : shopOrderList) {
            String itemCode = order.getItemO().getItemCode();
            Item item = itemInfo.stream()
                    .filter(i -> i.getItemCode().equals(itemCode))
                    .findFirst()
                    .orElse(null);



            if (item != null) {

                ShopOrderDto dto = ShopOrderDto.builder()
                        .orderNum(order.getOrderNum())
                        .orderCode(order.getOrderCode())
                        .orderCount(order.getOrderCount())
//                        .orderDate(order.getOrderDate())
                        .orderDate(order.getOrderDate())
                        .orderState(order.getOrderState())
                        .orderDelivery(order.getOrderDelivery())
                        .orderPayment(order.getOrderPayment())
                        .storeCode(order.getStoreO().getStoreCode())
                        .itemCode(item.getItemCode())
                        .build();
                //List에 추가
                shopOrderDtoList.add(dto);
            }
        }
        // 조회된 아이템들을 반환
        return shopOrderDtoList;
    }
    }
