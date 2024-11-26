package com.kong.cc.repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.OrderBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kong.cc.dto.CartDto;
import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Cart;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.entity.QCart;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QItemMajorCategory;
import com.kong.cc.entity.QItemMiddleCategory;
import com.kong.cc.entity.QItemSubCategory;
import com.kong.cc.entity.QShopOrder;
import com.kong.cc.entity.QStore;
import com.kong.cc.entity.QWishItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ShopDSLRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	
	
	//카테고리 전체 조회 -사이드바 목록
	public List<ItemMajorCategory>selectAllCategoriesList() {
		QItemMajorCategory major = QItemMajorCategory.itemMajorCategory;
		QItemMiddleCategory middle = QItemMiddleCategory.itemMiddleCategory;
		QItemSubCategory sub = QItemSubCategory.itemSubCategory;
		
		 //fetchJoin하면 단 한번의 쿼리로 데이터를 가져올 수 있음, 아니면 분류 마다 중,소분류 조회 하는 쿼리문이 실행되게 됨 (distinct필수) 
		return jpaQueryFactory
		        .selectFrom(major)
		        .distinct()
		        .leftJoin(middle)
		        .on(middle.itemMajorCategoryMd.eq(major))  
		        .fetchJoin()
		        .leftJoin(sub)
		        .on(sub.itemMiddleCategorySb.eq(middle))
		        .fetchJoin()
		        .orderBy(major.itemCategoryNum.asc())
		        .fetch();
	}
	
	//카테고리 선택 별 상품조회
	public List<Item> selectItemsByCategories(Integer majorNum,Integer middleNum,Integer subNum) {
		QItem item = QItem.item;

		//**orderby 추가해야함**
		
		List<Item> resultList = null;
		if(subNum!=null) {
			resultList= jpaQueryFactory.selectFrom(item)
					.where(item.itemSubCategory.itemCategoryNum.eq(subNum))
					.fetch();
		}
		if(middleNum !=null) {
			resultList= jpaQueryFactory.selectFrom(item)
					.where(item.itemMiddleCategory.itemCategoryNum.eq(middleNum))
					.fetch();
		}
		if(majorNum != null) {
			resultList= jpaQueryFactory.selectFrom(item)
					.where(item.itemMajorCategory.itemCategoryNum.eq(majorNum))
					.fetch();
		}
	
		return resultList;
	}
	
	//키워드,카테고리 선택 없는 전체 상품 조회
	public List<Item> selectAllItems() {
		QItem item = QItem.item;
		return jpaQueryFactory.selectFrom(item).fetch();
	}
	//키워드 검색 상품 조회
	public List<Item>selectItemsByKeyWord(String keyWord)  { 
		QItem item = QItem.item; 
		
		return jpaQueryFactory.selectFrom(item)
				.where(item.itemName.contains(keyWord))
				.fetch();
		
	}

	//상세페이지 관심 상품등록된 상품 여부 확인
	public Integer checkIsWishedItem (String itemCode,Integer storeCode) {

		QWishItem wishItem = QWishItem.wishItem;
	
		return jpaQueryFactory.select(wishItem.wishItemNum).from(wishItem)
				.where(wishItem.itemW.itemCode.eq(itemCode)
				.and(wishItem.storeW.storeCode.eq(storeCode))).fetchFirst(); 
			
	}
	
	//카테고리 선택 없는 관심상품 리스트
	public List<ItemDto>selectAllWishItems(Integer storeCode)  {
		QWishItem wishItem = QWishItem.wishItem;
		QItem item = QItem.item;
		
		return jpaQueryFactory.select(Projections.bean(ItemDto.class,
				item.itemName,
				item.itemMajorCategory,
				item.itemMiddleCategory,
				item.itemSubCategory,
				item.itemPrice,
				item.itemStorage,
				wishItem.wishItemNum))
				.from(item)
				.leftJoin(wishItem)
				.on(item.itemCode.eq(wishItem.itemW.itemCode))
				.where(wishItem.storeW.storeCode.eq(storeCode))
				.fetch();
	}	
	
	// 카테고리 선택 관심상품 리스트 
	public List<ItemDto> selectAllWishItemsByCategroy(Integer storeCode,Integer majorNum,Integer middleNum,Integer subNum) {
		QWishItem wishItem = QWishItem.wishItem;
		QItem item = QItem.item;	
		

		List<ItemDto> resultList = null;
		if(subNum!=null) {
			resultList=jpaQueryFactory.select(Projections.bean(ItemDto.class,
					item.itemName,
					item.itemMajorCategory,
					item.itemMiddleCategory,
					item.itemSubCategory,
					item.itemPrice,
					item.itemStorage,
					wishItem.wishItemNum))
					.from(item)
					.leftJoin(wishItem)
					.on(item.itemCode.eq(wishItem.itemW.itemCode))
					.where(wishItem.storeW.storeCode.eq(storeCode).and(item.itemSubCategory.itemCategoryNum.eq(subNum)))	
					.fetch();
		}
		if(middleNum !=null) {
			
			resultList=jpaQueryFactory.select(Projections.bean(ItemDto.class,
					item.itemName,
					item.itemMajorCategory,
					item.itemMiddleCategory,
					item.itemSubCategory,
					item.itemPrice,
					item.itemStorage,
					wishItem.wishItemNum))
					.from(item)
					.leftJoin(wishItem)
					.on(item.itemCode.eq(wishItem.itemW.itemCode))
					.where(wishItem.storeW.storeCode.eq(storeCode).and(item.itemMiddleCategory.itemCategoryNum.eq(middleNum)))	
					.fetch();
		}
		if(majorNum != null) {
			resultList=jpaQueryFactory.select(Projections.bean(ItemDto.class,
					item.itemName,
					item.itemMajorCategory,
					item.itemMiddleCategory,
					item.itemSubCategory,
					item.itemPrice,
					item.itemStorage,
					wishItem.wishItemNum))
					.from(item)
					.leftJoin(wishItem)
					.on(item.itemCode.eq(wishItem.itemW.itemCode))
					.where(wishItem.storeW.storeCode.eq(storeCode).and(item.itemMajorCategory.itemCategoryNum.eq(majorNum)))	
					.fetch();
		}
	
		return resultList;
	}
	
	// 관심상품 다중선택 삭제  (하나만 선택해도 가능)
	public long deleteCheckedWishItem(Integer storeCode,List<Integer>wishItemNumList) {
		
		QWishItem wishItem = QWishItem.wishItem;
		return jpaQueryFactory.delete(wishItem)
				.where(wishItem.storeW.storeCode.eq(storeCode).and(wishItem.wishItemNum.in(wishItemNumList)))
				.execute();
			// execute ->long 타입 반환은 성공한 행의 개수를 반환
	
	}
	//장바구니에 이미 등록된 상품 여부 확인
	public Integer checkIsExistedCartItem(Integer storeCode,String itemCode) {
		QCart cart = QCart.cart;
		return jpaQueryFactory.select(cart.cartNum)
				.from(cart)
				.where(cart.itemCa.itemCode.eq(itemCode)
						.and(cart.storeCa.storeCode.eq(storeCode)))
				.fetchOne();
	}
	
	//장바구니 리스트 조회
	public List<CartDto>selectAllCartItems(Integer storeCode) {
		QCart cart = QCart.cart;
		QItem item = QItem.item;
	
		List<Cart> cartList =  jpaQueryFactory.selectFrom(cart)
								.join(item)
								.on(item.itemCode.eq(cart.itemCa.itemCode))
								.fetchJoin()
								.where(cart.storeCa.storeCode.eq(storeCode))
								.fetch();
						
		return cartList.stream().map(c->CartDto.builder()
												.cartNum(c.getCartNum())
												.cartItemCount(c.getCartItemCount())
												.storeCode(c.getStoreCa().getStoreCode())
												.item(c.getItemCa().toDto())
												.build())
												.collect(Collectors.toList());
	}
	//장바구니 아이템 삭제
	public long deleteCartItem(Integer storeCode,Integer cartNum) {
		QCart cart = QCart.cart;
		
		return jpaQueryFactory.delete(cart)
							.where(cart.storeCa.storeCode.eq(storeCode).and(cart.cartNum.eq(cartNum)))
							.execute(); // 제대로 수행된 행의 개수 반환
					
	}
	
	// 그룹화해도 테이블 구조상 주문자 정보는 같이 반복되서 출력됨,Map형태로 주문자와 orderItem따로 분리시킬 예정
	//장바구니 선택 아이템 주문서 출력
	public List<CartDto> selectAllCartItemForOrder(Integer storeCode,List<Integer>cartItemNumList) {
		QCart cart = QCart.cart;
		QItem item = QItem.item;
		QStore store  = QStore.store;
		
		
		//필요한 정보만 조회 하기 위해 Projections 사용함
		return jpaQueryFactory
			        .select(Projections.bean(CartDto.class,
			            cart.cartNum,
			            cart.cartItemCount,
			            cart.storeCa.storeCode.as("storeCode"), // 조회 항목 같아서 별칭 줌
			            cart.itemCa.itemCode.as("itemCode"),
			            Projections.bean(ItemDto.class,
			                item.itemCode,
			                item.itemName,
			                item.itemPrice,
			                item.itemMajorCategory.itemCategoryNum.as("itemMajorCategoryNum"),
			                item.itemMajorCategory.itemCategoryName.as("itemMajorCategoryName"),
			                item.itemMiddleCategory.itemCategoryNum.as("itemMiddleCategoryNum"),
			                item.itemMiddleCategory.itemCategoryName.as("itemMiddleCategoryName"),
			                item.itemSubCategory.itemCategoryNum.as("itemSubCategoryNum"),
			                item.itemSubCategory.itemCategoryName.as("itemSubCategoryName"),
			                item.itemImageFile.fileNum.as("itemFileNum")
			            ).as("item"),
			            Projections.bean(StoreDto.class,
			                store.storeCode,
			            	store.storeName,
			                store.storeAddress,
			                store.storeAddressNum,
			                store.storePhone,
			                store.ownerName
			            ).as("store")
			        ))
			        .from(cart)
			        .join(item).on(cart.itemCa.itemCode.eq(item.itemCode))
			        .join(store).on(cart.storeCa.storeCode.eq(store.storeCode))
			        .where(cart.storeCa.storeCode.eq(storeCode)
			              .and(cart.cartNum.in(cartItemNumList)))
			        .groupBy(store.storeCode, cart.cartNum) // 가맹점과 장바구니 항목 기준 그룹화
			        .fetch();
		
	}
	
	
	
	public List<Cart> findCartsByCartNums(List<Integer> cartNums) {
	    QCart cart = QCart.cart;
	    QItem item = QItem.item;

	    return jpaQueryFactory
	        .selectFrom(cart)
	        .join(cart.itemCa, item).fetchJoin()
	        .where(cart.cartNum.in(cartNums))
	        .fetch();
	}

	//결제 성공시 주문에 해당되는 모든 장바구니 상품 삭제 
	public long deleteCartItems(Integer storeCode, List<Integer> cartNums) {
	    QCart cart = QCart.cart;
	    
	    return jpaQueryFactory
	        .delete(cart)
	        .where(cart.storeCa.storeCode.eq(storeCode)
	              .and(cart.cartNum.in(cartNums)))
	        .execute();  
	}
	
	// 최신순 정렬 주문내역 조회
	public List<ShopOrderDto> selectAllShopOrderList(Integer storeCode) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;

	    return jpaQueryFactory
	        .select(Projections.fields(ShopOrderDto.class,
	            order.orderNum,
	            order.orderCode,
	            order.orderCount,
	            order.orderDate,
	            order.orderState,
	            order.orderDelivery,
	            order.orderPayment,
	            store.storeCode,
	            item.itemCode))
	        .from(order)
	        .join(order.itemO, item)
	        .join(order.storeO, store)
	        .where(store.storeCode.eq(storeCode))
	        .orderBy(order.orderDate.desc())
	        .fetch();
	}

	public List<ShopOrderDto> selectAllShopOrderListByPeriod(Integer storeCode,Date startDate,Date endDate){
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;

	    
	    if(startDate !=null ||endDate !=null) {
	    	return	jpaQueryFactory
	    	        .select(Projections.fields(ShopOrderDto.class,
	    		            order.orderNum,
	    		            order.orderCode,
	    		            order.orderCount,
	    		            order.orderDate,
	    		            order.orderState,
	    		            order.orderDelivery,
	    		            order.orderPayment,
	    		            store.storeCode,
	    		            item.itemCode))
	    		        .from(order)
	    		        .join(order.itemO, item)
	    		        .join(order.storeO, store)
	    		        .where(store.storeCode.eq(storeCode).and(order.orderDate.between(startDate, endDate)))
	    		        .orderBy(order.orderDate.desc())
	    		        .fetch();
	    }
		return null;
		 
	}
	public List<ShopOrderDto> selectAllShopOrderListByOrderState(Integer storeCode,String orderState) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;
	    
	    return	jpaQueryFactory
    	        .select(Projections.fields(ShopOrderDto.class,
    		            order.orderNum,
    		            order.orderCode,
    		            order.orderCount,
    		            order.orderDate,
    		            order.orderState,
    		            order.orderDelivery,
    		            order.orderPayment,
    		            store.storeCode,
    		            item.itemCode))
    		        .from(order)
    		        .join(order.itemO, item)
    		        .join(order.storeO, store)
    		        .where(order.storeO.storeCode.eq(storeCode).and(order.orderState.eq(orderState)))
    		        .orderBy(order.orderDate.desc())
    		        .fetch();
	    
	}
	public List<ShopOrderDto> selectOneShopOrderByOrderCode(Integer storeCode, String orderCode) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;
	    

	    return	jpaQueryFactory
    	        .select(Projections.fields(ShopOrderDto.class,
    		            order.orderNum,
    		            order.orderCode,
    		            order.orderCount,
    		            order.orderDate,
    		            order.orderState,
    		            order.orderDelivery,
    		            order.orderPayment,
    		            store.storeCode,
    		            item.itemCode))
    		        .from(order)
    		        .join(order.itemO, item)
    		        .join(order.storeO, store)
    		        .where(order.storeO.storeCode.eq(storeCode).and(order.orderCode.eq(orderCode)))
    		        .orderBy(order.orderDate.desc())
    		        .fetch();
	    
	
	}
}
