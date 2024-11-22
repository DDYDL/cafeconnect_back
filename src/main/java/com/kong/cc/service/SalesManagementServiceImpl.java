package com.kong.cc.service;

import com.kong.cc.dto.SalesDto;
import com.kong.cc.dto.SalesItem;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.Sales;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.MenuRepository;
import com.kong.cc.repository.SalesRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        List<String> menuNameList = body.getSalesData().stream().map(SalesItem::getMenuName).collect(Collectors.toList());
        System.out.println("menuNameList = " + menuNameList);

        //salesCount 가져오기
        List<Integer> salesCountList = body.getSalesData().stream().map(SalesItem::getSalesCount).collect(Collectors.toList());
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
}

