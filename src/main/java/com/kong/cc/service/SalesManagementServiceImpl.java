package com.kong.cc.service;

import com.kong.cc.entity.Store;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.MenuSalesDto;
import com.kong.cc.dto.SalesDto;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.Sales;
import com.kong.cc.repository.MenuRepository;
import com.kong.cc.repository.SalesManagementDslRepository;
import com.kong.cc.repository.SalesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesManagementServiceImpl implements SalesManagementService {

    private final SalesRepository salesRepository;
    private final MenuRepository menuRepository;

    private final SalesManagementDslRepository salesManagementDslRepository;

    @Override
    public List<MenuDto> menuList() {
        return this.menuRepository.findAll(Sort.by(Sort.Direction.ASC, "menuCategory")).stream().map(Menu::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void salesWrite(Date salesDate, Integer storeCode, List<SalesDto> salesList) throws Exception {
        salesRepository.deleteBySalesDateAndStoreSa_StoreCode(salesDate, storeCode);

        System.out.println("salesDate1:"+salesDate);
        System.out.println("storeCode1:"+storeCode);
        System.out.println("salesList1:"+salesList);

        if(salesList.size()==0) return;
        List<Sales> salesEntityList = salesList.stream().map(s->s.toEntity()).collect(Collectors.toList());
        for(Sales s: salesEntityList) {
            System.out.println("salesDate2:"+salesDate);
            System.out.println("storeCode2:"+storeCode);
            s.setStoreSa(Store.builder().storeCode(storeCode).build());
            s.setSalesDate(salesDate);
            salesRepository.save(s);
        }
    }

    @Override
    public List<SalesDto> salesTemp(Date salesDate, Integer storeCode) throws Exception {
        return salesRepository.findBySalesDateAndStoreSa_StoreCode(salesDate, storeCode)
                .stream().map(s->s.toDto()).collect(Collectors.toList());
    }

    public List<MenuSalesDto> salesAnalisisByBetween(Integer storeCode, Date start, Date end) throws Exception {
        return salesManagementDslRepository.menuSalesByBtweenDate(storeCode, start, end);
    }

    @Override
    public Map<String,List<MenuSalesDto>> salesAnalysis(Integer storeCode, String period) throws Exception {
        Map<String, List<MenuSalesDto>> res = new TreeMap<>();
        if(period.equals("year")) { //연 매출내역
            Integer year1 = LocalDate.now().getYear();
            res.put(year1+"", salesManagementDslRepository.menuSalesByYear(storeCode, LocalDate.now().getYear()));
            res.put(year1-1+"", salesManagementDslRepository.menuSalesByYear(storeCode, LocalDate.now().minusYears(1).getYear()));
            res.put(year1-2+"", salesManagementDslRepository.menuSalesByYear(storeCode, LocalDate.now().minusYears(2).getYear()));
        } else if(period.equals("month")) { //월 매출 내역
            //이번달 매출내역
            LocalDate localdate = LocalDate.now();
            Integer year = localdate.getYear();
            Integer month = localdate.getMonthValue();
            res.put(year+"-"+month, salesManagementDslRepository.menuSalesByMonth(storeCode, year, month));

            //1달전,2달전,3달전,4달전,5달전 매출내역
            for(int i=1; i<6; i++) {
                localdate = LocalDate.now().minusMonths(i);
                year = localdate.getYear();
                month = localdate.getMonthValue();
                res.put(year+"-"+String.format("%02d", month), salesManagementDslRepository.menuSalesByMonth(storeCode, year, month));
            }
        } else if(period.equals("quart")) { //분기별 매출 내역
            LocalDate localdate = LocalDate.now();
            Integer year = localdate.getYear();
            Integer month = localdate.getMonthValue();
            Integer quart = month/3;
            QuartDate quartDate = getQuart(year, quart);
            res.put(year+"-"+quart+"분기", salesManagementDslRepository.menuSalesByBtweenDate(storeCode, quartDate.start, quartDate.end));
            for(int i=1; i<5; i++) {
                if (quart == 1) {
                    --year;
                    quart = 4;
                } else {
                    --quart;
                }
                System.out.println(year+":"+quart);
                quartDate = getQuart(year, quart);
                res.put(year+"-"+quart+"분기", salesManagementDslRepository.menuSalesByBtweenDate(storeCode, quartDate.start, quartDate.end));
            }
        }
        return res;
    }

    class QuartDate {
        private Date start;
        private Date end;
    }

    private QuartDate getQuart(Integer year, Integer quart) {
        LocalDate startDate, endDate;
        if(quart==1) { //1분기
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 3, 31);
        } else if(quart==2) {
            startDate = LocalDate.of(year, 4, 1);
            endDate = LocalDate.of(year, 6, 30);
        } else if(quart==3) {
            startDate = LocalDate.of(year, 7, 1);
            endDate = LocalDate.of(year, 9, 30);
        } else { //quart==4
            startDate = LocalDate.of(year, 10, 1);
            endDate = LocalDate.of(year, 12, 31);
        }
        //해당월의 마지막날짜
        endDate = endDate.withDayOfMonth(endDate.lengthOfMonth());

        //LocalDate => Date (sql)
        QuartDate quartMonth = new QuartDate();
        quartMonth.start = Date.valueOf(startDate);
        quartMonth.end = Date.valueOf(endDate);

        return quartMonth;
    }
};
