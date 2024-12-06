package com.kong.cc.service;

import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.SalesAnalysisResDto;
import com.kong.cc.dto.SalesAnnualDto;
import com.kong.cc.dto.SalesCustomDto;
import com.kong.cc.dto.SalesDto;
import com.kong.cc.dto.SalesMonthlyDto;
import com.kong.cc.dto.SalesQuarterlyDto;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.QMenu;
import com.kong.cc.entity.QSales;
import com.kong.cc.entity.Sales;
import com.kong.cc.repository.ItemRepository;
import com.kong.cc.repository.MenuRepository;
import com.kong.cc.repository.SalesRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesManagementServiceImpl implements SalesManagementService {

    private final SalesRepository salesRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final ItemRepository itemRepository;

    @Override
    public List<MenuDto> menuList() {
        return this.menuRepository.findAll().stream().map(Menu::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void salesWrite(Date salesDate, Integer storeCode, List<SalesDto> salesList)  {
        salesRepository.deleteBySalesDateAndStoreSa_StoreCode(salesDate, storeCode);
        if (salesList.size() == 0) return;
        List<Sales> salesEntityList = salesList.stream().map(s -> s.toEntity()).collect(Collectors.toList());
        for (Sales s : salesEntityList) {
            salesRepository.save(s);
        }
    }

    @Override
    public List<SalesDto> salesTemp(Date salesDate, Integer storeCode)  {
        return salesRepository.findBySalesDateAndStoreSa_StoreCode(salesDate, storeCode)
                .stream().map(s -> s.toDto()).collect(Collectors.toList());
    }

    @Override
    public List<SalesAnnualDto> annualSAnalysis(Integer storeCode)  {

        int currentYear = LocalDate.now().getYear(); // 현재 연도 2024
        Integer currentYearInt = LocalDate.now().getYear();
        Integer lastYearInt = LocalDate.now().getYear() - 1;
        Integer twoYearsAgo = LocalDate.now().getYear() - 2;

        // 2022년도 (제작년)
        LocalDate startDateMinus2Years = LocalDate.of(currentYear - 2, 1, 1); // 2022-01-01
        LocalDate endDateMinus2Years = LocalDate.of(currentYear - 2, 12, 31); // 2022-12-31

        // 2023년도 (작년)
        LocalDate startDateMinus1Year = LocalDate.of(currentYear - 1, 1, 1); // 2022-01-01
        LocalDate endDateMinus1Year = LocalDate.of(currentYear - 1, 12, 31); // 2022-12-31

        QMenu menu = QMenu.menu;
        QSales sales = QSales.sales;

        // SalesAnnualDto 리스트 생성
        List<SalesAnnualDto> salesAnnualDtoList = new ArrayList<>();

        // 매출 데이터 조회 (2022)
        List<Tuple> year2022 = jpaQueryFactory
                .select(menu.menuName, sales.salesCount, menu.menuCategory.menuCategoryName)
                .from(sales)
                .innerJoin(sales.menu, menu)
                .where(sales.salesDate.between(java.sql.Date.valueOf(startDateMinus2Years), java.sql.Date.valueOf(endDateMinus2Years)),
                        sales.storeSa.storeCode.eq(storeCode))
                .orderBy(menu.menuName.asc())
                .fetch();
        // 매출 데이터를 SalesAnnualDto에 매핑
        for (Tuple tuple : year2022) {
            SalesAnnualDto salesAnnualDto = new SalesAnnualDto();
            salesAnnualDto.setMenuName(tuple.get(menu.menuName));  // 메뉴 이름 설정
            salesAnnualDto.setSalesCount(tuple.get(sales.salesCount));  // 판매 수량 설정
            salesAnnualDto.setMenuCategoryName(tuple.get(menu.menuCategory.menuCategoryName));
            salesAnnualDto.setYear(twoYearsAgo);

            // salesAnnualDto 리스트에 추가
            salesAnnualDtoList.add(salesAnnualDto);
        }

        // 매출 데이터 조회 (2023)
        List<Tuple> year2023 = jpaQueryFactory
                .select(menu.menuName, sales.salesCount, menu.menuCategory.menuCategoryName)
                .from(sales)
                .innerJoin(sales.menu, menu)
                .where(sales.salesDate.between(java.sql.Date.valueOf(startDateMinus1Year), java.sql.Date.valueOf(endDateMinus1Year)),
                        sales.storeSa.storeCode.eq(storeCode))
                .orderBy(menu.menuName.asc())
                .fetch();
        // 매출 데이터를 SalesAnnualDto에 매핑
        for (Tuple tuple : year2023) {
            SalesAnnualDto salesAnnualDto = new SalesAnnualDto();
            salesAnnualDto.setMenuName(tuple.get(menu.menuName));  // 메뉴 이름 설정
            salesAnnualDto.setSalesCount(tuple.get(sales.salesCount));  // 판매 수량 설정
            salesAnnualDto.setMenuCategoryName(tuple.get(menu.menuCategory.menuCategoryName));
            salesAnnualDto.setYear(lastYearInt);

            // salesAnnualDto 리스트에 추가
            salesAnnualDtoList.add(salesAnnualDto);
        }

        // 매출 데이터 조회 (2024)
        List<Tuple> year2024 = jpaQueryFactory
                .select(menu.menuName, sales.salesCount, menu.menuCategory.menuCategoryName, menu.menuPrice)
                .from(sales)
                .innerJoin(sales.menu, menu)
                .where(sales.salesDate.between(java.sql.Date.valueOf(startDateMinus2Years), java.sql.Date.valueOf(endDateMinus2Years)),
                        sales.storeSa.storeCode.eq(storeCode))
                .orderBy(menu.menuName.asc())
                .fetch();
        // 매출 데이터를 SalesAnnualDto에 매핑
        for (Tuple tuple : year2024) {
            SalesAnnualDto salesAnnualDto = new SalesAnnualDto();
            salesAnnualDto.setMenuName(tuple.get(menu.menuName));  // 메뉴 이름 설정
            salesAnnualDto.setSalesCount(tuple.get(sales.salesCount));  // 판매 수량 설정
            salesAnnualDto.setMenuCategoryName(tuple.get(menu.menuCategory.menuCategoryName));
            salesAnnualDto.setPrice(tuple.get(menu.menuPrice));
            salesAnnualDto.setYear(currentYearInt);

            // salesAnnualDto 리스트에 추가
            salesAnnualDtoList.add(salesAnnualDto);
        }

        // 반환
        return salesAnnualDtoList;
    }

    // 분기별 분석
    @Override
    public List<SalesQuarterlyDto> quarterlyAnalysis(Integer storeCode)  {
        LocalDate now = LocalDate.now();

        int currentYear = now.getYear(); // 현재 연도
        int currentQuarter = (now.getMonthValue() - 1) / 3 + 1; // 현재 분기

        // 분기별 시작일과 종료일을 하드코딩으로 설정
        String[][] quarterDates = {
                {"01-01", "03-31"}, // 1분기: 1월 1일 ~ 3월 31일
                {"04-01", "06-30"}, // 2분기: 4월 1일 ~ 6월 30일
                {"07-01", "09-30"}, // 3분기: 7월 1일 ~ 9월 30일
                {"10-01", "12-31"}  // 4분기: 10월 1일 ~ 12월 31일
        };

        // 5분기 데이터를 담을 리스트
        List<SalesQuarterlyDto> salesQuarterlyDtoList = new ArrayList<>();

        // 분기별 데이터를 5년치 구하기
        for (int i = 0; i < 5; i++) {
            int year = currentYear;
            int quarter = currentQuarter - i;

            // 분기 번호가 0 이하로 내려가면 작년으로 넘어감
            if (quarter <= 0) {
                year--;  // 작년으로 넘어감
                quarter += 4; // 분기 번호가 0 이하로 내려갈 경우
            }

            // 분기별 시작일과 종료일을 배열에서 가져오기
            String startDateStr = year + "-" + quarterDates[quarter - 1][0];  // 해당 분기 시작일
            String endDateStr = year + "-" + quarterDates[quarter - 1][1];    // 해당 분기 종료일

            // 문자열을 java.sql.Date로 변환
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            QMenu menu = QMenu.menu;
            QSales sales = QSales.sales;

            // 해당 분기 매출 데이터 조회
            List<Tuple> quarterData = jpaQueryFactory
                    .select(menu.menuName, sales.salesCount, menu.menuCategory.menuCategoryName, menu.menuPrice, sales.salesDate)
                    .from(sales)
                    .innerJoin(sales.menu, menu)
                    .where(sales.salesDate.between(startDate, endDate),
                            sales.storeSa.storeCode.eq(storeCode))
                    .orderBy(menu.menuName.asc())
                    .fetch();

            // 매출 데이터를 SalesQuarterlyDto에 매핑
            for (Tuple tuple : quarterData) {
                SalesQuarterlyDto salesQuarterlyDto = new SalesQuarterlyDto();
                salesQuarterlyDto.setMenuName(tuple.get(menu.menuName));  // 메뉴 이름 설정
                salesQuarterlyDto.setSalesCount(tuple.get(sales.salesCount));  // 판매 수량 설정
                salesQuarterlyDto.setMenuCategoryName(tuple.get(menu.menuCategory.menuCategoryName));
                salesQuarterlyDto.setPrice(tuple.get(menu.menuPrice)); // 가격
                salesQuarterlyDto.setQuarter(quarter);
                salesQuarterlyDto.setSalesDate(tuple.get(sales.salesDate));
                System.out.println("salesQuarterlyDto" + salesQuarterlyDto);

                // salesQuarterlyDto 리스트에 추가
                salesQuarterlyDtoList.add(salesQuarterlyDto);
            }
        }
        System.out.println("salesQuarterlyDtoList" + salesQuarterlyDtoList);

        // 반환
        return salesQuarterlyDtoList;
    }

    public List<SalesMonthlyDto> monthlyAnalysis(Integer storeCode) {
        // 현재 날짜 및 6개월 전 날짜 계산
        LocalDate currentDate = LocalDate.now();

        List<SalesMonthlyDto> monthlyAnalysisList = new ArrayList<>();

        QMenu menu = QMenu.menu;
        QSales sales = QSales.sales;

        // 6개월 전부터 이번 달까지 반복 (6번 반복)
        for (int i = 0; i < 6; i++) {
            // i는 5부터 시작하여 0까지 반복 (6개월 전부터 이번 달까지)
            LocalDate startOfMonth = currentDate.minusMonths(i).withDayOfMonth(1); // 월의 첫 날
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // 월의 마지막 날

            LocalDate month = currentDate.minusMonths(i);
            Integer monthValue = month.getMonthValue(); // 월만 추출 (7)

            // 매출 데이터 조회
            List<Tuple> salesData = jpaQueryFactory
                    .select(menu.menuName, sales.salesCount, menu.menuCategory.menuCategoryName, menu.menuPrice, sales.salesDate)
                    .from(sales)
                    .innerJoin(sales.menu, menu)
                    .where(sales.salesDate.between(java.sql.Date.valueOf(startOfMonth), java.sql.Date.valueOf(endOfMonth)),
                            sales.storeSa.storeCode.eq(storeCode))
                    .orderBy(sales.salesDate.asc()) // 날짜 기준 오름차순 정렬
                    .fetch();

            // 조회된 매출 데이터를 SalesQuarterlyDto에 매핑
            for (Tuple tuple : salesData) {
                SalesMonthlyDto salesMonthlyDto = new SalesMonthlyDto();
                salesMonthlyDto.setMenuName(tuple.get(menu.menuName));  // 메뉴 이름 설정
                salesMonthlyDto.setSalesCount(tuple.get(sales.salesCount));  // 판매 수량 설정
                salesMonthlyDto.setMenuCategoryName(tuple.get(menu.menuCategory.menuCategoryName));
                salesMonthlyDto.setPrice(tuple.get(menu.menuPrice));  // 메뉴 가격 설정
                salesMonthlyDto.setSalesDate(tuple.get(sales.salesDate));  // 판매 날짜 설정
                salesMonthlyDto.setMonthValue(monthValue);

                monthlyAnalysisList.add(salesMonthlyDto);
            }
        }
        return monthlyAnalysisList;
    }

        @Override
        public List<SalesCustomDto> customAnalysis (Integer storeCode, Date startDate, Date endDate) {

            QMenu menu = QMenu.menu;
            QSales sales = QSales.sales;

            List<SalesCustomDto> salesCustomizeDtoList = new ArrayList<>();

            List<Tuple> customize = jpaQueryFactory
                    .select(menu.menuName, sales.salesCount, menu.menuCategory.menuCategoryName, menu.menuPrice)
                    .from(sales)
                    .innerJoin(sales.menu, menu)
                    .where(sales.salesDate.between(startDate,endDate),
                            sales.storeSa.storeCode.eq(storeCode))
                    .orderBy(menu.menuCategory.menuCategoryName.asc(), menu.menuName.asc())
                    .fetch();

            // 매출 데이터를 SalesAnnualDto에 매핑
            for (Tuple tuple : customize) {
                SalesCustomDto salesCustomDto = new SalesCustomDto();
                salesCustomDto.setMenuName(tuple.get(menu.menuName));  // 메뉴 이름 설정
                salesCustomDto.setSalesCount(tuple.get(sales.salesCount));  // 판매 수량 설정
                salesCustomDto.setMenuCategoryName(tuple.get(menu.menuCategory.menuCategoryName));
                salesCustomDto.setPrice(tuple.get(menu.menuPrice));

                // salesAnnualDto 리스트에 추가
                salesCustomizeDtoList.add(salesCustomDto);
            }
            return salesCustomizeDtoList;
        }
    }
