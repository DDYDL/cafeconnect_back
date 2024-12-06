package com.kong.cc.service;

import com.kong.cc.dto.*;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public List<SalesDetailDto> itemRevenue(Integer storeCode, Date startDate, Date endDate) throws Exception {
        QShopOrder shopOrder = QShopOrder.shopOrder;
        QItem item = QItem.item;
        QStore store = QStore.store;

        // 1. 조건에 맞는 아이템 정보 조회
        List<Tuple> orderList = jpaQueryFactory
                .select(
                        item.itemCode.as("itemCode"),
                        item.itemName.as("itemName"),
                        item.itemPrice.as("itemPrice"),
                        shopOrder.orderCount.sum().as("orderCount"),
                        item.itemMajorCategory.itemCategoryNum.as("itemMajorCategoryNum"),
                        item.itemMajorCategory.itemCategoryName.as("itemMajorCategoryName"),
                        item.itemMiddleCategory.itemCategoryNum.as("itemMiddleCategoryNum"),
                        item.itemMiddleCategory.itemCategoryName.as("itemMiddleCategoryName"),
                        item.itemSubCategory.itemCategoryNum.as("itemSubCategoryNum"),
                        item.itemSubCategory.itemCategoryName.as("itemSubCategoryName")
                )
                .from(shopOrder)
                .innerJoin(shopOrder.itemO, item)
                .where(shopOrder.orderDate.between(startDate, endDate),
                        shopOrder.storeO.storeCode.eq(storeCode))
                .groupBy(shopOrder.itemO.itemCode)
                .orderBy(
                        item.itemMajorCategory.itemCategoryName.asc(),
                        item.itemMiddleCategory.itemCategoryName.asc(),
                        item.itemSubCategory.itemCategoryName.asc()
                )
                .fetch();

        System.out.println(orderList);
        List<SalesDetailDto> salesDetails = orderList.stream()
                .map(tuple -> {
                    return SalesDetailDto.builder()
                            .itemCode(tuple.get(0,String.class))
                            .itemName(tuple.get(1,String.class))
                            .itemPrice(tuple.get(2,Integer.class))
                            .salesCount(tuple.get(3,Integer.class))
                            .salesAmount(1L*tuple.get(3,Integer.class)*tuple.get(2,Integer.class))
                            .itemMajorCategoryNum(tuple.get(4,Integer.class))
                            .itemMajorCategoryName(tuple.get(5,String.class))
                            .itemMiddleCategoryNum(tuple.get(6,Integer.class))
                            .itemMiddleCategoryName(tuple.get(7,String.class))
                            .itemSubCategoryNum(tuple.get(8,Integer.class))
                            .itemSubCategoryName(tuple.get(9,String.class))
                            .build();
                })
                .collect(Collectors.toList());
        return salesDetails;
    }
}