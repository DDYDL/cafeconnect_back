package com.kong.cc.service;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.SalesDto;
import com.kong.cc.dto.SalesItem;
import com.kong.cc.entity.*;
import com.kong.cc.repository.MenuRepository;
import com.kong.cc.repository.SalesRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesManagementServiceImpl implements SalesManagementService {

    private final SalesRepository salesRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public void salesWrite(SalesDto body) throws Exception {

        // 현재 인증된 사용자 정보 가져오기
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //menuName 가져오기
        List<String> menuNameList = body.getSalesData().stream().map(SalesItemForm::getMenuName).collect(Collectors.toList());
        System.out.println("menuNameList = " + menuNameList);

        //salesCount 가져오기
        List<Integer> salesCountList = body.getSalesData().stream().map(SalesItemForm::getSalesCount).collect(Collectors.toList());
        System.out.println("salesCountList = " + salesCountList);

        for (int i = 0; i < menuNameList.size(); i++) {
            String menuName = menuNameList.get(i);
            Integer salesCount = salesCountList.get(i);
            System.out.println("메뉴명: " + menuName + ", 수량: " + salesCount);

            //임시 store 코드
            Store storeCode = new Store();
            storeCode.setStoreCode(1);

            //menu코드로 저장해야해서, menuName으로 menuCode찾기
            String menuCode = this.menuRepository.findByMenuName(menuName).orElseThrow(() -> new Exception("menuName menuRepository에 없습니다")).getMenuCode();

            //menu object 찾아서, setMenu()에 넣어주기
            Menu menu = this.menuRepository.findByMenuCode(menuCode);

            // Sales 인스턴스 객체에 menuCode, salesDate,Count,Status등 넣어주기
            // Sales 엔티티 생성
            Sales sales = new Sales();
            sales.setSalesCount(salesCount);
            sales.setSalesDate(body.getSalesDate());
            sales.setSalesStatus(body.getSalesStatus());
            sales.setMenu(menu);
            sales.setStoreSa(storeCode);

            // Sales 저장
            salesRepository.save(sales);
        }
    }

    @Override
    public List<ItemDto> salesAnalysis(String period, Integer categoryId) {
        QSales sales = QSales.sales;
        QItem item = QItem.item;

        // 현재 날짜 기준
        LocalDate now = LocalDate.now();
        LocalDate startDate, endDate;
        LocalDate previousMonthStart, previousMonthEnd;
        LocalDate twoMonthsAgoStart, twoMonthsAgoEnd;

        switch (period) {
            case "monthly": // 월간
                // 이번 달 기준 데이터
                startDate = now.minusMonths(1).withDayOfMonth(1); // 전월의 첫날
                endDate = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth()); // 전월의 말일

                // 전전월 데이터
                previousMonthStart = now.minusMonths(2).withDayOfMonth(1); // 전전월의 첫날
                previousMonthEnd = now.minusMonths(2).withDayOfMonth(now.minusMonths(2).lengthOfMonth()); // 전전월의 말일
                break;

            case "quarterly": // 분기별
                int currentQuarter = (now.getMonthValue() - 1) / 3 + 1;
                startDate = now.withMonth((currentQuarter - 1) * 3 + 1).withDayOfMonth(1); // 분기 시작
                endDate = startDate.plusMonths(2).withDayOfMonth(startDate.plusMonths(2).lengthOfMonth()); // 분기 종료

                // 전분기 데이터는 구현 필요
                throw new UnsupportedOperationException("전분기 비교는 아직 구현되지 않았습니다.");
            case "yearly": // 연간
                startDate = now.withDayOfYear(1); // 연간 시작
                endDate = now.withDayOfYear(now.lengthOfYear()); // 연간 종료
                break;
            default: // 사용자 지정
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        // BooleanExpression 생성
//        BooleanExpression dateCondition = sales.salesDate.between(startDate, endDate);
//        BooleanExpression previousMonthCondition = sales.salesDate.between(previousMonthStart, previousMonthEnd);
        BooleanExpression categoryCondition = item.itemMajorCategory.itemCategoryNum.eq(categoryId);

        // 데이터 조회: 전월 데이터와 현재 월 데이터를 가져와 비교
        List<Tuple> currentResults = jpaQueryFactory
                .select(
                        item.itemCode,
                        item.itemName,
                        item.itemPrice,
                        item.itemMajorCategory.itemCategoryName
                )
                .from(sales)
//            .innerJoin(sales.menu.item, item)
                .where(
//                        dateCondition.and(
                                categoryCondition)
//                )
                .fetch();

        List<Tuple> previousResults = jpaQueryFactory
                .select(
                        item.itemCode,
                        item.itemName,
                        item.itemPrice,
                        item.itemMajorCategory.itemCategoryName
                )
                .from(sales)
//            .innerJoin(sales.menu.item, item)
                .where(
//                        previousMonthCondition.and(categoryCondition)
                )
                .fetch();

        // 4. 결과 매핑
        List<ItemDto> currentItems = currentResults.stream().map(tuple -> {
            ItemDto dto = new ItemDto();
            dto.setItemCode(tuple.get(item.itemCode));
            dto.setItemName(tuple.get(item.itemName));
            dto.setItemPrice(tuple.get(item.itemPrice));
//        dto.setMajorCategoryName(tuple.get(item.itemMajorCategory.itemCategoryName));
            return dto;
        }).collect(Collectors.toList());

        List<ItemDto> previousItems = previousResults.stream().map(tuple -> {
            ItemDto dto = new ItemDto();
            dto.setItemCode(tuple.get(item.itemCode));
            dto.setItemName(tuple.get(item.itemName));
            dto.setItemPrice(tuple.get(item.itemPrice));
//        dto.setMajorCategoryName(tuple.get(item.itemMajorCategory.itemCategoryName));
            return dto;
        }).collect(Collectors.toList());

        // 필요 시 데이터 비교를 위해 로직 추가 가능
        return currentItems; // 또는 비교 결과 반환
    }};