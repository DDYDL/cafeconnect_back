package com.kong.cc.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QShopOrder;
import com.kong.cc.entity.QStock;
import com.kong.cc.entity.QStore;
import com.kong.cc.entity.ShopOrder;
import com.kong.cc.entity.Stock;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockDslRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	public List<ShopOrder> selectOrderList(Integer storeCode) throws Exception {
		QShopOrder shopOrder = QShopOrder.shopOrder;
		QStore store = QStore.store;
		
		return jpaQueryFactory.selectFrom(shopOrder)
				.where(shopOrder.storeO.storeCode.eq(storeCode).and(shopOrder.orderState.eq("배송완료")))
				.fetch();
	}
	
	public List<Stock> selectStockByStoreCode(Integer storeCode) throws Exception {
		QStock stock = QStock.stock;
		
		return jpaQueryFactory.selectFrom(stock)
				.where(stock.storeSt.storeCode.eq(storeCode))
				.fetch();
	}
	
	public List<Stock> selectStockByCategory(Integer storeCode, Integer categoryNum, String expirationDate) throws Exception {
		QStock stock = QStock.stock;
		QItem item = QItem.item;
//		Expressions.dateTemplate(LocalDate.class, "DATE_FORMAT({0}, {1})", stock.stockExpirationDate, "%Y-%m-%d");
		
		return jpaQueryFactory.selectFrom(stock)
				.leftJoin(item)
				.on(item.itemCode.eq(stock.itemS.itemCode))
				.where(stock.storeSt.storeCode.eq(storeCode).and(item.itemSubCategory.itemCategoryNum.eq(categoryNum)))
//							.and((Expressions.dateTemplate(LocalDate.class, "DATE_FORMAT({0}, {1})", stock.stockExpirationDate, "%Y-%m-%d")) < 3)
				.fetch();
	}
	
	public List<Stock> selectStockByKeyword(Integer storeCode, String keyword) throws Exception {
		QStock stock = QStock.stock;
		QItem item = QItem.item;
		
		return jpaQueryFactory.selectFrom(stock)
				.leftJoin(item)
				.on(item.itemCode.eq(stock.itemS.itemCode))
				.where(stock.storeSt.storeCode.eq(storeCode).and(item.itemName.contains(keyword)))
				.fetch();
	}
}
