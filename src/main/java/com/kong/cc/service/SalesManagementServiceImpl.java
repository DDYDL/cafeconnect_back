package com.kong.cc.service;

import com.kong.cc.dto.SalesDto;
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
        // Sales 엔티티 생성
        Sales sales = new Sales();

        // 현재 인증된 사용자 정보 가져오기
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // menuName으로 MenuCode List 찾기
        Menu menuCode = this.menuRepository.findByMenuName(body.getMenuName())
                .orElseThrow(() -> new Exception("해당 메뉴 이름에 대한 메뉴 정보가 존재하지 않습니다."));

        Store store = storeRepository.findById(body.getStoreCode())
                .orElseThrow(() -> new Exception("해당 storeCode에 대한 Store 정보가 존재하지 않습니다."));

        // Menu 객체를 사용하여 Sales 엔티티 설정
        sales.setMenu(menuCode);
        sales.setStoreSa(store);
        sales.setSalesDate(body.getSalesDate());
        sales.setSalesCount(body.getSalesCount());
        sales.setSalesStatus(body.getSalesStatus());

        // Sales 저장
        salesRepository.save(sales);
    }

//
//    @Override
//    public SalesDto anualSales(Integer itemCategoryNum) throws Exception {
//        return null;
//    }
//
//    @Override
//    public SalesDto quarterlySales(Integer itemCategoryNum) throws Exception {
//        return null;
//    }
//
//    @Override
//    public SalesDto monthlySales(Integer itemCategoryNum) throws Exception {
//        return null;
//    }
//
//    @Override
//    public SalesDto customSales(Integer itemCategoryNum) throws Exception {
//        return null;
//    }
}
