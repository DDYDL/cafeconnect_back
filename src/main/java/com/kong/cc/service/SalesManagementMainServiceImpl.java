package com.kong.cc.service;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    public List<ItemDto> itemRevenue(
//            Date startDate, Date endDate,
            Integer storeCode) {
        QShopOrder shopOrder = QShopOrder.shopOrder; // Order 테이블
        QItem item = QItem.item; // Item 테이블
        QStore store = QStore.store; // Store 테이블

//        System.out.println("(service)startDate = " + startDate + "  (service)endDate = " + endDate);


        // 1. 가맹점 및 날짜 조건에 맞는 Item 정보 조회

        List<ShopOrder> orderList = jpaQueryFactory
                .selectFrom(shopOrder)
                .innerJoin(shopOrder.storeO)
                .innerJoin(shopOrder.itemO)
                .fetchJoin()
                .fetch();

        System.out.println(jpaQueryFactory
                .selectFrom(shopOrder)
                .innerJoin(shopOrder.storeO)
                .innerJoin(shopOrder.itemO)
                .fetchJoin());

        System.out.println("orderList = " + orderList);


        List<ItemDto> itemList = orderList.stream()
                .map(order -> ItemDto.builder()
                        .itemCode(order.getItemO().getItemCode())
                        .itemName(order.getItemO().getItemName())
                        .itemPrice(order.getItemO().getItemPrice())
                        .build())
                .collect(Collectors.toList());

        System.out.println("itemList = " + itemList);


        return itemList;

    }
};

//                List<ItemDto> result =  jpaQueryFactory
//                .select(
//                        Projections.constructor(
//                                ItemDto.class,
//                                item.itemCode,
//                                item.itemName,
//                                item.itemPrice,
//                                item.itemCapacity,
//                                item.itemUnitQuantity,
//                                item.itemUnit,
//                                item.itemStandard,
//                                item.itemStorage,
//                                item.itemCountryOrigin,
//                                item.itemMajorCategory.itemCategoryNum,
//                                item.itemMiddleCategory.itemCategoryNum,
//                                item.itemSubCategory.itemCategoryNum,
//                                item.itemImageFile.fileNum
//                        )
//                )
//                .from(shopOrder)
//                .join(shopOrder.storeO, store)
//                .join(shopOrder.itemO, item)
//                .where(
//                        shopOrder.orderDate.between(startDate, endDate),
//                        shopOrder.storeO.storeCode.eq(storeCode)
//                )
//                .fetch();


//        System.out.println("(service)startDate1 = " + startDate + "  (service)endDate1 = " + endDate);
//        System.out.println("(service)start = " + start + "  (service)end = " + end);
//        System.out.println("(service)storeCode = " + storeCode);



//
//        System.out.println("(service)startDate1 = " + startDate + "  (service)endDate1 = " + endDate);
////        System.out.println("(service)start = " + start + "  (service)end = " + end);
//        System.out.println("(service)storeCode = " + storeCode);
//
//        System.out.println("result = " + result);  // 빈 리스트일 경우 확인

//        return result;

//    }
//};

