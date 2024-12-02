package com.kong.cc.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QShopOrder;
import com.kong.cc.entity.QStock;
import com.kong.cc.entity.QStore;
import com.kong.cc.entity.ShopOrder;
import com.kong.cc.entity.Stock;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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
	
	public List<Stock> selectStockByCategory(Integer storeCode, Map<String, String> param, String expirationDate) throws Exception {
		QStock stock = QStock.stock;
		QItem item = QItem.item;
		List<Stock> stockList = new ArrayList<>();
		
		// 받아온 expirationDate를 날짜 양식으로 	바꿈
		// StringTemplate expirationDateFormat = Expressions.stringTemplate("DATE_FORMAT( {0}, {1} )", expirationDate, ConstantImpl.create("%Y-%m-%d"));

		if(param.get("category").equals("major")) {
			Integer categoryNum = Integer.parseInt(param.get("categoryNum"));

			stockList = jpaQueryFactory.selectFrom(stock)
					.leftJoin(item)
					.on(item.itemCode.eq(stock.itemS.itemCode))
					.where(stock.storeSt.storeCode.eq(storeCode).and(item.itemMajorCategory.itemCategoryNum.eq(categoryNum)))
					.where(expirationDateSelect(expirationDate, stock.stockExpirationDate))
					.fetch();
		} else if(param.get("category").equals("middle")) {
			Integer categoryNum = Integer.parseInt(param.get("categoryNum"));

			stockList = jpaQueryFactory.selectFrom(stock)
					.leftJoin(item)
					.on(item.itemCode.eq(stock.itemS.itemCode))
					.where(stock.storeSt.storeCode.eq(storeCode).and(item.itemMiddleCategory.itemCategoryNum.eq(categoryNum)))
					.where(expirationDateSelect(expirationDate, stock.stockExpirationDate))
					.fetch();
		} else if(param.get("category").equals("sub")) {
			Integer categoryNum = Integer.parseInt(param.get("categoryNum"));

			stockList = jpaQueryFactory.selectFrom(stock)
					.leftJoin(item)
					.on(item.itemCode.eq(stock.itemS.itemCode))
					.where(stock.storeSt.storeCode.eq(storeCode).and(item.itemSubCategory.itemCategoryNum.eq(categoryNum)))
					.where(expirationDateSelect(expirationDate, stock.stockExpirationDate))
					.fetch();
		} else { // 카테고리 안 넘어오면 모두 선택
			stockList = jpaQueryFactory.selectFrom(stock)
					.leftJoin(item)
					.on(item.itemCode.eq(stock.itemS.itemCode))
					.where(stock.storeSt.storeCode.eq(storeCode))
					.where(expirationDateSelect(expirationDate, stock.stockExpirationDate))
					.fetch();
		}

		return stockList;
	}
	
	// 유통기한이 뭐라도 넘어오면 유통기한이 3일 이하로 남은 stock만 가져옴
	private BooleanExpression expirationDateSelect(String expirationDate, DatePath<Date> stockExpirationDate) {
		// 현재 날짜 기준 expirationDate와 마이너스 연산을 한다.(유통기한이 3일 이하인 stock만 선택)
		NumberTemplate dateFormat = Expressions.numberTemplate(Integer.class, "DATEDIFF({0}, curdate())", stockExpirationDate);
		return StringUtils.hasText(expirationDate) ? dateFormat.loe(3) : null;
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
	
	// 상품 선택 시 해당 상품의 재고가 있는 가맹점과 재고 수 가져오기
	public List<Tuple> selectStoreByItemCode(String itemCode) {
		QStore store = QStore.store;
		QStock stock = QStock.stock;
		
		return jpaQueryFactory.select(store, stock.stockCount)
				.from(store)
				.leftJoin(stock)
				.on(stock.storeSt.storeCode.eq(store.storeCode))
				.where(stock.itemS.itemCode.eq(itemCode))
				.fetch();
	}

	public List<Stock> selectStockByExpirationDate(String expirationDate) throws Exception {
		QStock stock = QStock.stock;
		QItem item = QItem.item;

		return jpaQueryFactory.selectFrom(stock)
					.leftJoin(item)
					.on(item.itemCode.eq(stock.itemS.itemCode))
					.where(expirationDateSelect(expirationDate, stock.stockExpirationDate))
					.fetch();
	}

	public List<Stock> selectStockByStockCount(Integer count) throws Exception {
		QStock stock = QStock.stock;
		QItem item = QItem.item;

		return jpaQueryFactory.selectFrom(stock)
					.leftJoin(item)
					.on(item.itemCode.eq(stock.itemS.itemCode))
					.where(stock.stockCount.loe(count))
					.fetch();
	}
}