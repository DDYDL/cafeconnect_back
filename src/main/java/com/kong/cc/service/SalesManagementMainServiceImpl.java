package com.kong.cc.service;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesManagementMainServiceImpl implements SalesManagementMainService {

    private final ShopOrderRepository shopOrderRepository;
    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;
    private final ItemMajorCategoryRepository itemMajorCategoryRepository;
    private final ItemMiddleCategoryRepository itemMiddleCategoryRepository;
    private final ItemSubCategoryRepository itemSubCategoryRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ShopOrderDto> itemRevenue(Date startDate, Date endDate, Integer storeCode) {
        QShopOrder shopOrder = QShopOrder.shopOrder;
        QItem item = QItem.item;

        // 1. Order 테이블에서 storeCode와 start/endDate가 일치하는 OrderList
        List<ShopOrder> shopOrderList = jpaQueryFactory
                .selectFrom(shopOrder)
                .where(
                        shopOrder.orderDate.between(startDate, endDate),
                        shopOrder.storeO.storeCode.eq(storeCode)
                )
                .fetch();

        // 2. Order에서 가져온 itemCode 목록을 사용해 아이템 정보 조회
        List<String> itemCodeList = shopOrderList.stream()
                .map(order -> order.getItemO().getItemCode())
                .collect(Collectors.toList());

        // itemCode 목록을 사용해 Item 정보를 가져오기
        List<Item> itemList = itemRepository.findByItemCodeIn(itemCodeList);

        // ShopOrderDto 리스트를 위한 결과 저장
        List<ShopOrderDto> shopOrderDtoList = new ArrayList<>();

        // 아이템 정보 및 주문 정보 매칭
        for (ShopOrder order : shopOrderList) {
            String itemCode = order.getItemO().getItemCode();

            // 해당 itemCode에 맞는 아이템 정보 찾기
            Item itemInfo = itemList.stream()
                    .filter(i -> i.getItemCode().equals(itemCode))
                    .findFirst()
                    .orElse(null);

            if (itemInfo != null) {
                // 카테고리 정보 가져오기
                Integer majorCategoryNum = itemInfo.getItemMajorCategory() != null ? itemInfo.getItemMajorCategory().getItemCategoryNum() : null;
                Integer middleCategoryNum = itemInfo.getItemMiddleCategory() != null ? itemInfo.getItemMiddleCategory().getItemCategoryNum() : null;
                Integer subCategoryNum = itemInfo.getItemSubCategory() != null ? itemInfo.getItemSubCategory().getItemCategoryNum() : null;

                // ShopOrderDto 생성
                ShopOrderDto dto = ShopOrderDto.builder()
                        .orderNum(order.getOrderNum())
                        .orderCode(order.getOrderCode())
                        .orderCount(order.getOrderCount())
                        .orderState(order.getOrderState())
                        .orderDelivery(order.getOrderDelivery())
                        .orderPayment(order.getOrderPayment())
                        .storeCode(order.getStoreO().getStoreCode())
                        .itemCode(itemInfo.getItemCode())  // itemCode

//                        .majorCategoryNum(majorCategoryNum)  // 카테고리 정보
//                        .middleCategoryNum(middleCategoryNum)  // 카테고리 정보
//                        .subCategoryNum(subCategoryNum)  // 카테고리 정보
                        .build();

                // 리스트에 추가
                shopOrderDtoList.add(dto);
            }
        }

        return shopOrderDtoList;
    }};