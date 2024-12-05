package com.kong.cc.service;

import com.kong.cc.dto.*;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
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

    public List<ItemDto> itemRevenue(Integer storeCode, Date startDate, Date endDate) throws Exception {
        QShopOrder shopOrder = QShopOrder.shopOrder;
        QItem item = QItem.item;
        QStore store = QStore.store;

        // 1. 조건에 맞는 아이템 정보 조회
        List<Tuple> orderList = jpaQueryFactory
                .select(
                        item.itemCode,
                        item.itemName,
                        item.itemPrice,
                        shopOrder.orderCount.sum(),
                        item.itemStandard,
                        item.itemMajorCategory.itemCategoryNum,
                        item.itemMajorCategory.itemCategoryName,
                        item.itemMiddleCategory.itemCategoryNum,
                        item.itemMiddleCategory.itemCategoryName,
                        item.itemSubCategory.itemCategoryNum,
                        item.itemSubCategory.itemCategoryName,
                        shopOrder.orderCount
                        //shoporder.ordercount필요 // sum계산해서 프론트로 넘기는 부분 필요.
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

        System.out.println("orderList" + orderList);

        List<ItemDto> itemList = orderList.stream()
                .map(tuple -> {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setItemCode(tuple.get(item.itemCode));
                    itemDto.setItemName(tuple.get(item.itemName));
                    itemDto.setItemPrice(tuple.get(item.itemPrice));
                    itemDto.setItemMajorCategoryName(tuple.get(item.itemMajorCategory.itemCategoryName));
                    itemDto.setItemMiddleCategoryName(tuple.get(item.itemMiddleCategory.itemCategoryName));
                    itemDto.setItemSubCategoryName(tuple.get(item.itemSubCategory.itemCategoryName));
                    itemDto.setItemMajorCategoryNum(tuple.get(item.itemMajorCategory.itemCategoryNum));
                    itemDto.setItemMiddleCategoryNum(tuple.get(item.itemMiddleCategory.itemCategoryNum));
                    itemDto.setItemSubCategoryNum(tuple.get(item.itemSubCategory.itemCategoryNum));
                    itemDto.setItemStandard(tuple.get(item.itemStandard));
                    itemDto.setOrderCount(tuple.get(shopOrder.orderCount));
                    return itemDto;
                })
                .collect(Collectors.toList());
        return itemList;
    }
}