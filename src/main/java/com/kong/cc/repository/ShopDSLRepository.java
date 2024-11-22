package com.kong.cc.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QWishItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ShopDSLRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
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
	@Transactional
	public long deleteCheckedWishItem(Integer storeCode,List<Integer>wishItemNumList) {
		
		QWishItem wishItem = QWishItem.wishItem;
		return jpaQueryFactory.delete(wishItem)
				.where(wishItem.storeW.storeCode.eq(storeCode).and(wishItem.wishItemNum.in(wishItemNumList)))
				.execute();
			// execute ->long 타입 반환은 영향을 받은(성공한 행의 개수를 반환)
	
	}	
}
