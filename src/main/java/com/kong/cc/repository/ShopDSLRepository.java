package com.kong.cc.repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.kong.cc.dto.CartDto;
import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ItemExpenseDto;
import com.kong.cc.dto.OrderItemGroupByCodeDto;
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
import com.kong.cc.entity.ShopOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
	
	//카테고리 선택 별 상품조회 +페이징
	public List<Item> selectItemsByCategories(Integer majorNum,Integer middleNum,Integer subNum,PageRequest pageRequest) {
		QItem item = QItem.item;

		//**orderby 추가해야함**
		
		List<Item> resultList = null;
		if(subNum!=null) {
			resultList= jpaQueryFactory.selectFrom(item)
					.where(item.itemSubCategory.itemCategoryNum.eq(subNum))
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		if(middleNum !=null) {
			resultList= jpaQueryFactory.selectFrom(item)
					.where(item.itemMiddleCategory.itemCategoryNum.eq(middleNum))
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		if(majorNum != null) {
			resultList= jpaQueryFactory.selectFrom(item)
					.where(item.itemMajorCategory.itemCategoryNum.eq(majorNum))
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
	
		return resultList;
	}
	//카테고리 선택 별 총 개수 
	public Long selectCountItemsByCategories(Integer majorNum,Integer middleNum,Integer subNum) {
		QItem item = QItem.item;
		Long count = null;
		
		if(subNum!=null) {
			count= jpaQueryFactory.select(item.count()).from(item)
					.where(item.itemSubCategory.itemCategoryNum.eq(subNum))
					.fetchOne();
		}
		if(middleNum !=null) {
			count= jpaQueryFactory.select(item.count()).from(item)
					.where(item.itemMiddleCategory.itemCategoryNum.eq(middleNum))
					.fetchOne();
		}
		if(majorNum != null) {
			count= jpaQueryFactory.select(item.count()).from(item)
					.where(item.itemMajorCategory.itemCategoryNum.eq(majorNum))
					.fetchOne();
		}
		return count;
	}
	
	// 카테고리 별 상품조회 + 주문 수량 정렬 
	public List<Item> selectItemsByCategoriesWithSort(Integer majorNum,Integer middleNum,Integer subNum) {
		QItem item = QItem.item;
		QShopOrder order = QShopOrder.shopOrder;

		//동적 쿼리 사용 BooleanBuilder	.and,.or로 조건을 누적시킬 수 있음 
		BooleanBuilder builder = new BooleanBuilder();
		   
		   if(subNum != null) {
		       builder.and(item.itemSubCategory.itemCategoryNum.eq(subNum));
		   } else if(middleNum != null) {
		       builder.and(item.itemMiddleCategory.itemCategoryNum.eq(middleNum));
		   } else if(majorNum != null) {
		       builder.and(item.itemMajorCategory.itemCategoryNum.eq(majorNum));
		   }

		   return jpaQueryFactory
		       .selectFrom(item)
		       .leftJoin(order).on(order.itemO.eq(item))
		       .where(builder) // 동적쿼리 
		       .groupBy(item)
		       .orderBy(order.orderCount.sum().coalesce(0).desc()) //coalesce 만약에 주문량이 없으면 0으로 함 
		       .fetch();
	}

	//키워드,카테고리 선택 없는 전체 상품 조회
	public List<Item> selectAllItems(PageRequest pageRequest) {
		QItem item = QItem.item;
		return jpaQueryFactory
				//.select(item,item.itemImageFile.fileName)
				.selectFrom(item)
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
	//키워드,카테고리 선택 없는 전체 상품 총 개수 
	public Long selectAllCountItem() {
		QItem item = QItem.item;
		return jpaQueryFactory.select(item.count()).from(item).fetchOne();
	}
	//키워드 검색 상품 조회
	public List<Item>selectItemsByKeyWord(String keyWord,PageRequest pageRequest)  { 
		QItem item = QItem.item; 
		
		return jpaQueryFactory.selectFrom(item)
				.where(item.itemName.contains(keyWord))
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
	//키워드 검색 상품 총 개수
	public  Long selectAllCountItemsByKeyWord(String keyWord)  { 
		QItem item = QItem.item; 
		
		return jpaQueryFactory.select(item.count()).from(item)
				.where(item.itemName.contains(keyWord))
				.fetchOne();
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
				item.itemCode,
				item.itemName,
				item.itemMajorCategory,
				item.itemMiddleCategory,
				item.itemSubCategory,
				item.itemPrice,
				item.itemImageFile.fileNum.as("itemFileNum"),
				item.itemImageFile.fileName.as("itemFileName"),
				item.itemStorage,
				wishItem.wishItemNum
				))
				.from(item)
				.leftJoin(wishItem)
				.on(item.itemCode.eq(wishItem.itemW.itemCode))
			    .leftJoin(item.itemMajorCategory)
			    .leftJoin(item.itemMiddleCategory)
			    .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌
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
					item.itemCode,
					item.itemName,
					item.itemMajorCategory,
					item.itemMiddleCategory,
					item.itemSubCategory,
					item.itemPrice,
					item.itemImageFile.fileNum.as("itemFileNum"),
					item.itemImageFile.fileName.as("itemFileName"),
					item.itemStorage,
					wishItem.wishItemNum))
					.from(item)
					.leftJoin(wishItem)
					.on(item.itemCode.eq(wishItem.itemW.itemCode))
				    .leftJoin(item.itemMajorCategory)
				    .leftJoin(item.itemMiddleCategory)
				    .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌
					.where(wishItem.storeW.storeCode.eq(storeCode).and(item.itemSubCategory.itemCategoryNum.eq(subNum)))	
					.fetch();
		}
		if(middleNum !=null) {
			
			resultList=jpaQueryFactory.select(Projections.bean(ItemDto.class,
					item.itemCode,
					item.itemName,
					item.itemMajorCategory,
					item.itemMiddleCategory,
					item.itemSubCategory,
					item.itemPrice,
					item.itemImageFile.fileNum.as("itemFileNum"),
					item.itemImageFile.fileName.as("itemFileName"),
					item.itemStorage,
					wishItem.wishItemNum))
					.from(item)
					.leftJoin(wishItem)
					.on(item.itemCode.eq(wishItem.itemW.itemCode))
				    .leftJoin(item.itemMajorCategory)
				    .leftJoin(item.itemMiddleCategory)
				    .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌
					.where(wishItem.storeW.storeCode.eq(storeCode).and(item.itemMiddleCategory.itemCategoryNum.eq(middleNum)))	
					.fetch();
		}
		if(majorNum != null) {
			resultList=jpaQueryFactory.select(Projections.bean(ItemDto.class,
					item.itemCode,
					item.itemName,
					item.itemMajorCategory,
					item.itemMiddleCategory,
					item.itemSubCategory,
					item.itemPrice,
					item.itemImageFile.fileNum.as("itemFileNum"),
					item.itemImageFile.fileName.as("itemFileName"),
					item.itemStorage,
					wishItem.wishItemNum))
					.from(item)
					.leftJoin(wishItem)
					.on(item.itemCode.eq(wishItem.itemW.itemCode))
				    .leftJoin(item.itemMajorCategory)
				    .leftJoin(item.itemMiddleCategory)
				    .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌
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
	//장바구니 리스트 카운트
	public Long selectCountCartList(Integer storeCode) {
		QCart cart = QCart.cart;
		return 	jpaQueryFactory.select(cart.count())
				.from(cart)
				.where(cart.storeCa.storeCode.eq(storeCode))
				.fetchOne();
		
	}
	
	
	// 그룹화해도 테이블 구조상 주문자 정보는 같이 반복되서 출력됨,Map형태로 주문자와 orderItem따로 분리시킬 예정
	//장바구니 선택 아이템 주문서 출력
	public List<CartDto> selectAllCartItemForOrder(Integer storeCode,List<Integer>cartItemNumList) {
		QCart cart = QCart.cart;
		QItem item = QItem.item;
		QStore store  = QStore.store;
		
		
		//필요한 정보만 조회 하기 위해 Projections 사용함 (직접 참조시 주의 할 점 null인 형태의 crossjoin 꼭! null처리 해줘야함!)
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
			                item.itemStorage,
			                item.itemMajorCategory.itemCategoryNum.as("itemMajorCategoryNum"),
			                item.itemMajorCategory.itemCategoryName.as("itemMajorCategoryName"),
			                item.itemMiddleCategory.itemCategoryNum.as("itemMiddleCategoryNum"),
			                item.itemMiddleCategory.itemCategoryName.as("itemMiddleCategoryName"),
			                item.itemSubCategory.itemCategoryNum.as("itemSubCategoryNum"),
			                item.itemSubCategory.itemCategoryName.as("itemSubCategoryName"),
			                item.itemImageFile.fileNum.as("itemFileNum"),
			                item.itemImageFile.fileName.as("itemFileName")
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
			        .leftJoin(item).on(cart.itemCa.itemCode.eq(item.itemCode))
			        .leftJoin(store).on(cart.storeCa.storeCode.eq(store.storeCode))
			        .leftJoin(item.itemMajorCategory)
			        .leftJoin(item.itemMiddleCategory)
			        .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌 
			        .where(cart.storeCa.storeCode.eq(storeCode).and(cart.cartNum.in(cartItemNumList)))
			        .fetch();
		
	}
	
	
	//장바구니 찾기
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
	//스케줄링 -당일 구매건 오휴 6 주문접수->주문 확인으로 바꾸기 
	public void confirmOrderState() {
		QShopOrder order = QShopOrder.shopOrder;
		
		// 당일 날짜로 설정
        Date today = new Date(System.currentTimeMillis());

        // 당일 주문 접수 건 주문확인으로 변경
        long updatedCount = jpaQueryFactory.update(order)
            .set(order.orderState, "주문확인")
            .where(order.orderState.eq("주문접수")
                .and(order.orderDate.eq(today)))
            .execute();
	}
	
	
	//최근 주문 날짜 10개 받아오기
	public List<String> selectPreOrderedDate(Integer storeCode) {
		 QShopOrder order = QShopOrder.shopOrder;
		 QStore store = QStore.store;

		 // 쿼리문  DATE_FORMAT(shopOrder.orderDate,"%Y-%m-%d") -> QueryDsl 표현법
		 return jpaQueryFactory.select(Expressions.stringTemplate("DATE_FORMAT({0}, {1})",order.orderDate,ConstantImpl.create("%Y-%m-%d")))
				 		.from(order)
				 		.join(order.storeO,store)
				 		.where(store.storeCode.eq(storeCode))
				 		.groupBy(order.orderDate)
				 		.limit(10)
				 		.offset(0)
				 		.orderBy(order.orderDate.desc())
				 		.fetch();
	}


	//가맹점 주문내역- 전체 및 날짜,주문 상태에따라 동적으로 조회
	public Map<String,Object> selectAllShopOrderListForStore(Integer storeCode,Date startDate,Date endDate,String orderState,PageRequest pageRequest){
		 QShopOrder order = QShopOrder.shopOrder;
		   QItem item = QItem.item;
		   QStore store = QStore.store;
		   
		   BooleanBuilder builder = new BooleanBuilder();
		   builder.and(store.storeCode.eq(storeCode));
		   
		   if (startDate != null && endDate != null) {
		       builder.and(order.orderDate.between(startDate, endDate));
		   }
		   
		   if (orderState != null && !orderState.equals("")) {
		       builder.and(order.orderState.eq(orderState));
		   }

		   
		   // 페이징된 orderCode 목록 조회
		    List<String> orderCodes = jpaQueryFactory
		        .select(order.orderCode)
		        .from(order)
		        .join(order.storeO, store)
		        .where(builder)
		        .groupBy(order.orderCode)
		        .orderBy(order.orderDate.desc())
		        .offset(pageRequest.getOffset())
		        .limit(pageRequest.getPageSize())
		        .fetch();
		    
		    // 선택된 주문의 상세 정보와 상품 목록 조회
		    List<OrderItemGroupByCodeDto> orders = jpaQueryFactory
		        .select(Projections.bean(OrderItemGroupByCodeDto.class,
		            order.orderCode,
		            order.orderDate,
		            order.orderState,
		            order.orderCount.sum().as("totalCount"),
		            item.itemPrice.multiply(order.orderCount).sum().as("totalAmount")
		        ))
		        .from(order)
		        .join(order.itemO, item)
		        .join(order.storeO, store)
		        .where(order.orderCode.in(orderCodes))
		        .groupBy(order.orderCode)
		        .orderBy(order.orderDate.desc())
		        .fetch();
		    
		    // 각 주문별 상품명 목록 조회 (상품명만 따로 출력하면 됨)
		    for (OrderItemGroupByCodeDto orderGroup : orders) {
		        List<String> itemNames = jpaQueryFactory
		            .select(item.itemName)
		            .from(order)
		            .join(order.itemO, item)
		            .where(order.orderCode.eq(orderGroup.getOrderCode()))
		            .fetch();
		        orderGroup.setItemNames(itemNames); //dto에 생성한 List<String> itemName 설정 
		    }
		    
		 // 전체 주문 수
		    Long totalCount = jpaQueryFactory
		        .select(order.orderCode.countDistinct())
		        .from(order)
		        .join(order.storeO, store)
		        .where(builder)
		        .fetchOne();
		    
		    
		    Map<String, Object> result = new HashMap<>();
		    result.put("orders", orders);
		    result.put("totalCount", totalCount);
		    
		    return result;
//		   return jpaQueryFactory.select(Projections.bean(ShopOrderDto.class,
//						           order.orderNum,
//						           order.orderCode,
//						           order.orderCount, 
//						           order.orderDate,
//						           order.orderState,
//						           order.orderDelivery,
//						           order.orderPayment,
//						           store.storeCode,
//						           item.itemCode,
//						           item.itemName,
//						           item.itemPrice
//						           ))
//						       .from(order)
//						       .join(order.itemO, item)
//						       .join(order.storeO, store)
//						       .where(builder)
//						       .orderBy(order.orderDate.desc()) //최신순 
//						       .fetch();
		}
	
	

	//주문 취소 전 접수상태 확인
	public List<ShopOrder> checkedListForCancelOrder(Integer storeCode, String orderCode) {
		QShopOrder order = QShopOrder.shopOrder;
		return jpaQueryFactory
		        .select(order)
		        .from(order)
		        .where(order.storeO.storeCode.eq(storeCode)
		        .and(order.orderCode.eq(orderCode))
		        .and(order.orderState.eq("주문접수"))).fetch();	       
	}
	
	
	//주문 상세 보기 
	public List<ShopOrderDto> selectOneShopOrderByOrderCode(Integer storeCode, String orderCode) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;
	    

	    return	jpaQueryFactory
    	        		.select(Projections.bean(ShopOrderDto.class,
    		    		order.impUid,
    		    		order.orderNum,
    		            order.orderCode,
    		            order.orderCount,
    		            order.orderDate,
    		            order.orderState,
    		            order.orderDelivery,
    		            order.orderPayment,
    		            store.storeCode,
    		            store.storeName,
    		            store.storeAddressNum,
    		            store.storeAddress,
    		            store.storePhone,
    		            store.ownerName,
    		            item.itemCode,
    		            item.itemName,
    		            item.itemStorage,
    		            item.itemImageFile.fileNum.as("itemFileNum"),
		                item.itemImageFile.fileName.as("itemFileName"),
    		            item.itemMajorCategory.itemCategoryName.as("itemMajorCategoryName"),
    		            item.itemMiddleCategory.itemCategoryName.as("itemMiddleCategoryName"),
    		            item.itemSubCategory.itemCategoryName.as("itemSubCategoryName"),
    		            item.itemPrice,
    		            item.itemPrice.multiply(order.orderCount)
    		            .as("orderPrice")
    	        		))
    		        .from(order)
    		        .leftJoin(order.itemO, item)
    		        .leftJoin(order.storeO, store)
			        .leftJoin(item.itemMajorCategory)
			        .leftJoin(item.itemMiddleCategory)
			        .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌
    		        .where(order.storeO.storeCode.eq(storeCode).and(order.orderCode.eq(orderCode)))
    		        .orderBy(order.orderDate.desc())
    		        .fetch();

	}
	//본사 주문내역- 전체가맹점,검색,기간 조회 포함 (동적쿼리 사용)
	public List<ShopOrderDto> selectMainStoreOrderList(Date startDate, Date endDate, String searchType, String keyword) {
		    
		    QShopOrder order = QShopOrder.shopOrder;
		    QItem item = QItem.item;
		    QStore store = QStore.store;
		    
		    BooleanBuilder builder = new BooleanBuilder();
		    
		    // 기간
		    if(startDate != null && endDate != null) {
		        builder.and(order.orderDate.between(startDate, endDate));
		    }
		    
		    // 검색어
		    if(searchType != null && keyword != null && !keyword.trim().isEmpty()) {
		        switch(searchType) {
		            case "storeName": //가맹점이름
		                builder.and(store.storeName.contains(keyword));
		                break;
		            case "orderState": //주문상태
		                builder.and(order.orderState.contains(keyword));
		                break;
		            case "itemName": //상풍명
		                builder.and(item.itemName.contains(keyword));
		                break;
		        }
		    }
			
		    return jpaQueryFactory
		        .select(Projections.bean(ShopOrderDto.class,
		            order.orderNum,
		            order.orderCode,
		            order.orderCount,
		            order.orderDate,
		            order.orderState,
		            order.orderDelivery,
		            order.orderPayment,
		            store.storeCode,
		            store.storeName,
		            store.storeAddress,
		            store.storePhone,
		            item.itemCode,
		            item.itemName,
		            item.itemPrice,
		            item.itemStorage,
		            order.impUid
		        ))
		        .from(order)
		        .join(order.itemO, item)
		        .join(order.storeO, store)
		        .where(builder)
		        .orderBy(order.orderDate.desc())
		        .fetch();
		}
	
	// 주문 상태 변경 
	public int updateOrderStatus(String orderCode, String orderState) {
	    QShopOrder order = QShopOrder.shopOrder;
	    
	    return (int) jpaQueryFactory
	        .update(order)
	        .set(order.orderState, orderState)
	        .where(order.orderCode.eq(orderCode))
	        .execute();
	}
	public List<ShopOrderDto> selectOrderByOrderCode(String orderCode) {
		 QShopOrder order = QShopOrder.shopOrder;
		    QItem item = QItem.item;
		    QStore store = QStore.store;
		    

		    return	jpaQueryFactory
	    	        .select(Projections.bean(ShopOrderDto.class,
	    		            order.impUid,
	    	        		order.orderNum,
	    		            order.orderCode,
	    		            order.orderCount,
	    		            order.orderDate,
	    		            order.orderState,
	    		            order.orderDelivery,
	    		            order.orderPayment,
	    		            store.storeCode,
	    		            store.storeName,
	    		            store.storeAddressNum,
	    		            store.storeAddress,
	    		            store.storePhone,
	    		            store.ownerName,
	    		            item.itemCode,
	    		            item.itemName,
	    		            item.itemStorage,
	    		            item.itemImageFile.fileNum.as("itemFileNum"),
			                item.itemImageFile.fileName.as("itemFileName"),
	    		            item.itemMajorCategory.itemCategoryName.as("itemMajorCategoryName"),
	    		            item.itemMiddleCategory.itemCategoryName.as("itemMiddleCategoryName"),
	    		            item.itemSubCategory.itemCategoryName.as("itemSubCategoryName"),
	    		            item.itemPrice,
	    		            item.itemPrice.multiply(order.orderCount)
	    		            .as("orderPrice")
	    	        		))
		    	        .from(order)
		    	        .leftJoin(order.itemO, item)
		    	        .leftJoin(order.storeO, store)
				        .leftJoin(item.itemMajorCategory)
				        .leftJoin(item.itemMiddleCategory)
				        .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌
				        .where(order.orderCode.eq(orderCode))
				        .orderBy(order.orderDate.desc())
				        .fetch();
		
	}
	
	
	//지출내역 시작
	//기간 선택 주문 총 수 
	public Long countOrders(Integer storeCode, Date startDate, Date endDate) {
	    QShopOrder order = QShopOrder.shopOrder;
	    
	    return jpaQueryFactory
	        .select(order.orderCode.countDistinct())
	        .from(order)
	        .where(order.storeO.storeCode.eq(storeCode)
	            .and(order.orderDate.between(startDate, endDate)))
	        .fetchOne();
	}
	// 기간 내 총 상품 주문 상품 통계 -단가,총 주문 개수 및 금액
	public List<ItemExpenseDto> selectExpnseItemList(Integer storeCode,Date startDate,Date endDate){
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;
	    QItemSubCategory subCategory = QItemSubCategory.itemSubCategory;
	 
	    if(startDate !=null ||endDate !=null) {

	    	return 	jpaQueryFactory.select(Projections.bean(ItemExpenseDto.class,
			    			item.itemCode,
				            item.itemName, // 상품명
				            item.itemMajorCategory.itemCategoryName.as("majorCategoryName"),
				            item.itemMajorCategory.itemCategoryNum.as("majorCategoryNum"),
				            item.itemMiddleCategory.itemCategoryName.as("middleCategoryName"),
				            item.itemMiddleCategory.itemCategoryNum.as("middleCategoryNum"),
				            item.itemSubCategory.itemCategoryName.coalesce("없음").as("subCategoryName"),
				            item.itemSubCategory.itemCategoryNum.as("subCategoryNum"),
				            item.itemPrice, //단가
				            order.orderCount.sum().as("totalOrderCount"), // 총 주문 개수
				            item.itemPrice.multiply(order.orderCount).sum().as("totalOrderPrice") // 구매 상품 총 금액(개수*단가)
				            ))
	    					.from(order)
		    		        .leftJoin(order.itemO, item) 
		    		        .leftJoin(item.itemSubCategory,subCategory) // leftJoin으로 Null이 포함되도록 함 (소분류 없는 상품 존재함, 대,중분류는 Null없) 
		    		        .join(order.storeO,store)
		    		        .where(store.storeCode.eq(storeCode).and(order.orderDate.between(startDate, endDate)))
		    		        .groupBy(item.itemCode)
		    		        .fetch();
	   
	    }
	    	return null;
	}
	//대분류 주문 상품 통계
	public List<ItemExpenseDto> getExpenseItemSummeryByMajorCategroy(Integer storeCode,Date startDate,Date endDate) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;

	    if(startDate !=null ||endDate !=null) {

	   return 	jpaQueryFactory.select(Projections.bean(ItemExpenseDto.class,
			   		item.itemMajorCategory.itemCategoryName.as("majorCategoryName"),
			   		item.itemMajorCategory.itemCategoryNum.as("majorCategoryNum"),
			   		order.orderCount.sum().as("totalOrderCount"), // 총 주문 개수
    		        item.itemPrice.multiply(order.orderCount).sum().as("totalOrderPrice"),
    		        item.itemCode.countDistinct().as("rowspanCount")))  //rowspan counts
    		        .from(order)
    		        .join(order.itemO, item)
    		        .join(order.storeO, store)
    		        .where(store.storeCode.eq(storeCode).and(order.orderDate.between(startDate, endDate)))
    		        .groupBy(item.itemMajorCategory.itemCategoryNum)
    		        .fetch();   
	    }
	    return null;
	}
	//중분류 주문 상품 통계
	public List<ItemExpenseDto> getExpenseItemSummeryByMiddleCategroy(Integer storeCode,Date startDate,Date endDate) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;

	    if(startDate !=null ||endDate !=null) {

	   return 	jpaQueryFactory.select(Projections.bean(ItemExpenseDto.class,
			   		item.itemMajorCategory.itemCategoryName.as("majorCategoryName"),
			   		item.itemMajorCategory.itemCategoryNum.as("majorCategoryNum"),
			   		item.itemMiddleCategory.itemCategoryName.as("middleCategoryName"),
			   		item.itemMiddleCategory.itemCategoryNum.as("middleCategoryNum"),
			   		order.orderCount.sum().as("totalOrderCount"), // 총 주문 개수
    		        item.itemPrice.multiply(order.orderCount).sum().as("totalOrderPrice"),
    		        item.itemCode.countDistinct().as("rowspanCount")))  //rowspan counts
    		        .from(order)
    		        .join(order.itemO, item)
    		        .join(order.storeO, store)
    		        .where(store.storeCode.eq(storeCode).and(order.orderDate.between(startDate, endDate)))
    		        .groupBy(item.itemMiddleCategory.itemCategoryNum)
    		        .fetch();   
	    }
	    return null;
	}
	//소분류 주문 상품 통계
	public List<ItemExpenseDto> getExpenseItemSummeryBySubCategroy(Integer storeCode,Date startDate,Date endDate) {
	    QShopOrder order = QShopOrder.shopOrder;
	    QItem item = QItem.item;
	    QStore store = QStore.store;
	    QItemSubCategory subCategory = QItemSubCategory.itemSubCategory;
	    if(startDate !=null ||endDate !=null) {

	   return 	jpaQueryFactory.select(Projections.bean(ItemExpenseDto.class,
					item.itemMajorCategory.itemCategoryName.as("majorCategoryName"),
			   		item.itemMajorCategory.itemCategoryNum.as("majorCategoryNum"),
			   		item.itemMiddleCategory.itemCategoryName.as("middleCategoryName"),
			   		item.itemMiddleCategory.itemCategoryNum.as("middleCategoryNum"),
			   		item.itemSubCategory.itemCategoryName.as("subCategoryName"),
			   		item.itemSubCategory.itemCategoryNum.as("subCategoryNum"),
				   	order.orderCount.sum().as("totalOrderCount"), // 총 주문 개수
    		        item.itemPrice.multiply(order.orderCount).sum().as("totalOrderPrice"),
    		        item.itemCode.countDistinct().as("rowspanCount")))  //rowspan counts
    		        .from(order)
    		        .leftJoin(order.itemO, item) 
    		        .leftJoin(item.itemSubCategory,subCategory) //대,중분류는 Null없음,소분류 없는 상품이 있으니 leftJoin으로 Null이 포함되도록 함 
    		        .join(order.storeO, store)
    		        .where(store.storeCode.eq(storeCode).and(order.orderDate.between(startDate, endDate)))
    		        .groupBy(item.itemSubCategory.itemCategoryNum)
    		        .fetch();   
	    }
	    return null;
	}
}