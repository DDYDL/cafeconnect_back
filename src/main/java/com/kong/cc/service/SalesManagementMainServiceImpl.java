package com.kong.cc.service;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QShopOrder;
import com.kong.cc.entity.QStore;
import com.kong.cc.repository.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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
    public List<ItemDto> itemRevenue(Date startDate, Date endDate, Integer storeCode) {
        QShopOrder shopOrder = QShopOrder.shopOrder; // Order 테이블
        QItem item = QItem.item; // Item 테이블
        QStore store = QStore.store; // Store 테이블

        System.out.println("(service)startDate = " + startDate + "  (service)endDate = " + endDate);

        // LocalDate를 Date로 변환하는 방법
//        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());




        // 1. 가맹점 및 날짜 조건에 맞는 Item 정보 조회

                List<ItemDto> result =  jpaQueryFactory
                .select(
                        Projections.constructor(
                                ItemDto.class,
                                item.itemCode,
                                item.itemName,
                                item.itemPrice,
                                item.itemCapacity,
                                item.itemUnitQuantity,
                                item.itemUnit,
                                item.itemStandard,
                                item.itemStorage,
                                item.itemCountryOrigin,
                                item.itemMajorCategory.itemCategoryNum,
                                item.itemMiddleCategory.itemCategoryNum,
                                item.itemSubCategory.itemCategoryNum,
                                item.itemImageFile.fileNum
                        )
                )
                .from(shopOrder)
                .join(shopOrder.storeO, store)
                .join(shopOrder.itemO, item)
                .where(
                        // Date를 LocalDate로 변환하여 비교
//                        Expressions.dateTemplate(LocalDate.class, "{0}", shopOrder.orderDate).between(startDate, endDate),
//                        shopOrder.storeO.storeCode.eq(storeCode)

                        shopOrder.orderDate.between(startDate, endDate),
                        shopOrder.storeO.storeCode.eq(storeCode)
                )
                .fetch();

//        System.out.println("(service)startDate = " + startDate + "  (service)endDate = " + endDate);
//        System.out.println("(service)start = " + start + "  (service)end = " + end);
//        System.out.println("(service)storeCode = " + storeCode);

        System.out.println("result = " + result);  // 빈 리스트일 경우 확인

        return result;
    }
};


//                .stream()
//                .map(t -> {
//                    ItemDto itemDto = Objects.requireNonNull(t.get(0, Item.class)).toDto();
//                    itemDto.setItemCode(t.get(1, String.class));
//                    itemDto.setItemName(t.get(2, String.class));
//                    itemDto.setItemPrice(t.get(3, Integer.class));
//                    itemDto.setItemCapacity(t.get(4, String.class));
//                    itemDto.setItemUnitQuantity(t.get(5,Integer.class));
//                    itemDto.setItemUnit(t.get(6, String.class));
//                    itemDto.setItemStandard(t.get(7, String.class));
//                    itemDto.setItemStorage(t.get(8, String.class));
//                    itemDto.setItemCountryOrigin(t.get(9, String.class));
//                    itemDto.setItemMajorCategoryNum(t.get(10, Integer.class));
//                    itemDto.setItemMiddleCategoryNum(t.get(11, Integer.class));
//                    itemDto.setItemSubCategoryNum(t.get(12, Integer.class));
//                    itemDto.setItemFileNum(t.get(13, Integer.class));
//
//                    System.out.println("itemDto = " + itemDto);
//
//                    return itemDto;
//
//                })
//                .collect(Collectors.toList());
//    }
