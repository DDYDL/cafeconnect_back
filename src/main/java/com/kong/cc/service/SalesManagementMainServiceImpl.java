package com.kong.cc.service;

import com.kong.cc.dto.*;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public List<ItemMajorCategoryForm> itemRevenue(Integer storeCode, Date startDate, Date endDate) throws Exception {
        QShopOrder shopOrder = QShopOrder.shopOrder;
        QItem item = QItem.item;
        QStore store = QStore.store;

        // 1. 조건에 맞는 아이템 정보 조회
        List<Tuple> orderList = jpaQueryFactory
                .select(item.itemCode, item.itemName, item.itemPrice, item.itemCapacity,
                        item.itemUnitQuantity, item.itemUnit, item.itemStandard, item.itemStorage,
                        item.itemCountryOrigin, item.itemMajorCategory.itemCategoryNum,
                        item.itemMiddleCategory.itemCategoryNum, item.itemSubCategory.itemCategoryNum,
                        item.itemImageFile.fileNum)
                .from(shopOrder)
                .innerJoin(shopOrder.storeO, store)
                .innerJoin(shopOrder.itemO, item)
                .where(shopOrder.orderDate.between(startDate, endDate),
                        shopOrder.storeO.storeCode.eq(storeCode))
                .fetch();


        // 대분류, 중분류, 소분류를 담을 맵을 생성
        Map<Integer, ItemMajorCategoryForm> majorCategoryMap = new HashMap<>(); // 대분류
        Map<Integer, ItemMiddleCategoryForm> middleCategoryMap = new HashMap<>(); // 중분류

        // 아이템 리스트를 생성
        List<ItemDto> itemList = orderList.stream()
                .map(order -> {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setItemCode(order.get(item.itemCode));
                    itemDto.setItemName(order.get(item.itemName));
                    itemDto.setItemPrice(order.get(item.itemPrice));
                    itemDto.setItemCapacity(order.get(item.itemCapacity));
                    itemDto.setItemUnitQuantity(order.get(item.itemUnitQuantity));
                    itemDto.setItemUnit(order.get(item.itemUnit));
                    itemDto.setItemStandard(order.get(item.itemStandard));
                    itemDto.setItemStorage(order.get(item.itemStorage));
                    itemDto.setItemCountryOrigin(order.get(item.itemCountryOrigin));
                    itemDto.setItemFileNum(order.get(item.itemImageFile.fileNum));
                    itemDto.setItemMajorCategoryNum(order.get(item.itemMajorCategory.itemCategoryNum));
                    itemDto.setItemMiddleCategoryNum(order.get(item.itemMiddleCategory.itemCategoryNum));
                    itemDto.setItemSubCategoryNum(order.get(item.itemSubCategory.itemCategoryNum));

                    Integer majorCategoryNum = itemDto.getItemMajorCategoryNum();
                    Integer middleCategoryNum = itemDto.getItemMiddleCategoryNum();
                    Integer subCategoryNum = itemDto.getItemSubCategoryNum();

                    // 대분류, 중분류, 소분류 정보 생성
                    ItemMajorCategoryForm majorCategoryForm = majorCategoryMap.computeIfAbsent(majorCategoryNum, k -> {
                        Optional<ItemMajorCategory> majorCategoryOptional = itemMajorCategoryRepository.findByItemCategoryNum(k);
                        return majorCategoryOptional.map(majorCategory -> {
                            ItemMajorCategoryForm form = new ItemMajorCategoryForm();
                            form.setItemCategoryNum(k);
                            form.setItemCategoryName(majorCategory.getItemCategoryName());
                            form.setMidCategories(new ArrayList<>());
                            return form;
                        }).orElse(null);
                    });

                    if (majorCategoryForm != null) {
                        List<ItemMiddleCategoryForm> midCategories = majorCategoryForm.getMidCategories();
                        ItemMiddleCategoryForm middleCategoryForm = midCategories.stream()
                                .filter(mid -> mid.getItemCategoryNum().equals(middleCategoryNum))
                                .findFirst()
                                .orElseGet(() -> {
                                    Optional<ItemMiddleCategory> middleCategoryOptional = itemMiddleCategoryRepository.findByItemCategoryNum(middleCategoryNum);
                                    ItemMiddleCategory middleCategory = middleCategoryOptional.orElse(null);
                                    ItemMiddleCategoryForm newForm = new ItemMiddleCategoryForm();
                                    newForm.setItemCategoryNum(middleCategoryNum);
                                    newForm.setItemCategoryName(middleCategory != null ? middleCategory.getItemCategoryName() : "");
                                    newForm.setSubCategories(new ArrayList<>());
                                    midCategories.add(newForm);
                                    return newForm;
                                });

                        // 소분류 추가
                        if (subCategoryNum != null) {
                            Optional<ItemSubCategory> subCategoryOptional = itemSubCategoryRepository.findByItemCategoryNum(subCategoryNum);
                            ItemSubCategory subCategory = subCategoryOptional.orElse(null);

                            ItemSubCategoryForm subCategoryForm = middleCategoryForm.getSubCategories().stream()
                                    .filter(s -> s.getItemCategoryNum().equals(subCategoryNum))
                                    .findFirst()
                                    .orElseGet(() -> {
                                        ItemSubCategoryForm newForm = new ItemSubCategoryForm();
                                        if (subCategory != null) {
                                            newForm.setItemCategoryNum(subCategoryNum);
                                            newForm.setItemCategoryName(subCategory.getItemCategoryName());
                                        }
                                        middleCategoryForm.getSubCategories().add(newForm);
                                        return newForm;
                                    });

                        // ItemDto에서 필요한 값만 직접 설정
                            Item itemEntity = new Item();
                            itemEntity.setItemCode(itemDto.getItemCode());
                            itemEntity.setItemName(itemDto.getItemName());
                            itemEntity.setItemPrice(itemDto.getItemPrice());
                            itemEntity.setItemCapacity(itemDto.getItemCapacity());
                            itemEntity.setItemUnitQuantity(itemDto.getItemUnitQuantity());
                            itemEntity.setItemUnit(itemDto.getItemUnit());
                            itemEntity.setItemStandard(itemDto.getItemStandard());
                            itemEntity.setItemStorage(itemDto.getItemStorage());
                            itemEntity.setItemCountryOrigin(itemDto.getItemCountryOrigin());
//                            itemEntity.setItemImageFile(itemDto.getItemFileNum());

                            subCategoryForm.getItemList().add(itemEntity);

                        } else {
//                            middleCategoryForm.getItemList().add(itemDto.toEntity());
                        }
                    }

                    return itemDto;
                })
                .collect(Collectors.toList());

        // 대분류가 있으면 대분류만 반환하고, 대분류+중분류가 있으면 중분류까지 반환
        return majorCategoryMap.values().stream()
                .map(majorCategoryForm -> {
                    ItemMajorCategoryForm majorCategoryDto = new ItemMajorCategoryForm();
                    majorCategoryDto.setItemCategoryNum(majorCategoryForm.getItemCategoryNum());
                    majorCategoryDto.setItemCategoryName(majorCategoryForm.getItemCategoryName());
//                    majorCategoryDto.setItemList(majorCategoryForm.getItemList());
                    majorCategoryDto.setMidCategories(majorCategoryForm.getMidCategories().stream()
                            .map(middleCategoryForm -> {
                                ItemMiddleCategoryForm middleCategoryDto = new ItemMiddleCategoryForm();
                                middleCategoryDto.setItemCategoryNum(middleCategoryForm.getItemCategoryNum());
                                middleCategoryDto.setItemCategoryName(middleCategoryForm.getItemCategoryName());
                                middleCategoryDto.setSubCategories(middleCategoryForm.getSubCategories().stream()
                                        .map(subCategoryForm -> {
                                            ItemSubCategoryForm subCategoryDto = new ItemSubCategoryForm();
                                            subCategoryDto.setItemCategoryNum(subCategoryForm.getItemCategoryNum());
                                            subCategoryDto.setItemCategoryName(subCategoryForm.getItemCategoryName());
                                            subCategoryDto.setItemList(subCategoryForm.getItemList()); // 필요한 값만 반환
                                            return subCategoryDto;
                                        }).collect(Collectors.toList()));
                                return middleCategoryDto;
                            }).collect(Collectors.toList()));
                    return majorCategoryDto;
                })
                .collect(Collectors.toList());
    }};