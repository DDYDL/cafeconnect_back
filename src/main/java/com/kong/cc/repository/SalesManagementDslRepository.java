package com.kong.cc.repository;

import com.kong.cc.dto.MenuSalesDto;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kong.cc.entity.QMenu;
import com.kong.cc.entity.QSales;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class SalesManagementDslRepository {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<MenuSalesDto> menuSalesByYear(Integer storeCode, Integer year) throws Exception {
        QSales sales = QSales.sales;
        QMenu menu = QMenu.menu;

        List<MenuSalesDto> salesList = jpaQueryFactory
                .select(Projections.bean(MenuSalesDto.class,
                        menu.menuCode.as("menuCode"),
                        menu.menuName.as("menuName"),
                        menu.menuPrice.as("menuPrice"),
                        menu.menuCategory.menuCategoryNum.as("menuCategoryNum"),
                        menu.menuCategory.menuCategoryName.as("menuCategoryName"),
                        sales.salesCount.sum().as("salesCount"),
                        sales.salesAmount.sum().as("salesAmount")
                ))
                .from(menu)
                .leftJoin(sales)
                .on(menu.menuCode.eq(sales.menu.menuCode))
                .where(sales.storeSa.storeCode.eq(storeCode)
                        .and(sales.salesDate.year().eq(year)))
                .groupBy(menu.menuCode)
                .orderBy(menu.menuCategory.menuCategoryNum.asc())
                .orderBy(menu.menuName.asc())
                .fetch();

        return salesList;
    }

    public List<MenuSalesDto> menuSalesByMonth(Integer storeCode, Integer year, Integer month) throws Exception {
        QSales sales = QSales.sales;
        QMenu menu = QMenu.menu;

        List<MenuSalesDto> salesList = jpaQueryFactory
                .select(Projections.bean(MenuSalesDto.class,
                        menu.menuCode.as("menuCode"),
                        menu.menuName.as("menuName"),
                        menu.menuPrice.as("menuPrice"),
                        menu.menuCategory.menuCategoryNum.as("menuCategoryNum"),
                        menu.menuCategory.menuCategoryName.as("menuCategoryName"),
                        sales.salesCount.sum().as("salesCount"),
                        sales.salesAmount.sum().as("salesAmount")
                ))
                .from(menu)
                .leftJoin(sales)
                .on(menu.menuCode.eq(sales.menu.menuCode))
                .where(sales.storeSa.storeCode.eq(storeCode)
                        .and(sales.salesDate.year().eq(year).and(
                                sales.salesDate.month().eq(month))))
                .groupBy(menu.menuCode)
                .orderBy(menu.menuName.asc())
                .fetch();

        return salesList;
    }

    public List<MenuSalesDto> menuSalesByBtweenDate(Integer storeCode, Date start, Date end) throws Exception {
        QSales sales = QSales.sales;
        QMenu menu = QMenu.menu;

        List<MenuSalesDto> salesList = jpaQueryFactory
                .select(Projections.bean(MenuSalesDto.class,
                        menu.menuCode.as("menuCode"),
                        menu.menuName.as("menuName"),
                        menu.menuPrice.as("menuPrice"),
                        menu.menuCategory.menuCategoryNum.as("menuCategoryNum"),
                        menu.menuCategory.menuCategoryName.as("menuCategoryName"),
                        sales.salesCount.sum().as("salesCount"),
                        sales.salesAmount.sum().as("salesAmount")
                ))
                .from(menu)
                .leftJoin(sales)
                .on(menu.menuCode.eq(sales.menu.menuCode))
                .groupBy(menu.menuCode)
                .where(sales.storeSa.storeCode.eq(storeCode)
                        .and(sales.salesDate.between(start, end)))
                .orderBy(menu.menuName.asc())
                .fetch();



        return salesList;
    }
}
