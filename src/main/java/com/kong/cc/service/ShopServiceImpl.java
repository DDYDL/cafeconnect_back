package com.kong.cc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.Store;
import com.kong.cc.entity.WishItem;
import com.kong.cc.repository.ItemRepository;
import com.kong.cc.repository.ShopDSLRepository;
import com.kong.cc.repository.WishItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

	private final ShopDSLRepository shopDslRepo;
	private final ItemRepository itemRepo;
	private final WishItemRepository wishItemRepo;
	
	
	// 카테고리 별 아이템 리스트 (검색X)
	@Override
	public List<ItemDto> selectItemsByCategroy(Integer majorNum,Integer middleNum,Integer subNum) throws Exception {
			
		return shopDslRepo.selectItemsByCategories(majorNum, middleNum, subNum).stream().map(item->item.toDto()).collect(Collectors.toList());
	}

	//검색된 아이템 리스트 
	@Override
	public List<ItemDto> selectItemsByKeyword(String keyword) throws Exception {

		List<ItemDto> itemList = null;
		// null이면 전체 조회
		if (keyword == null || keyword.trim().equals("")) {
			itemList = shopDslRepo.selectAllItems().stream().map(i -> i.toDto()).collect(Collectors.toList());
		} else {
			// keyword가 있는 경우 상품명
			itemList = shopDslRepo.selectItemsByKeyWord(keyword).stream().map(i->i.toDto()).collect(Collectors.toList());
		}
		return itemList;
	}

	@Override
	public ItemDto selectItem(String itemCode) throws Exception {
		return itemRepo.findById(itemCode).orElseThrow(()->new Exception("상품 번호 오류")).toDto();
	}

	@Override
	public Integer checkIsWished(String itemCode, Integer storeCode) throws Exception {
		return shopDslRepo.checkIsWishedItem(itemCode, storeCode);
	}

	//상세 페이지 관심상품 토글 - 관심 상품 등록, 삭제 
	@Override
	public Boolean toggleWishItem(String itemCode, Integer storeCode) throws Exception {
		//이미 등록된 상품인지 확인
		Integer wishNum = shopDslRepo.checkIsWishedItem(itemCode, storeCode);
		// 등록 안됨
		if(wishNum == null) {
			   WishItem wishItem= WishItem.builder()
					   			  .itemW(Item.builder().itemCode(itemCode).build())
					   			  .storeW(Store.builder().storeCode(storeCode).build())
					   			  .build();
			   wishItemRepo.save(wishItem);
			   return true;
		//등록됨	   
		}else {
		   wishItemRepo.deleteById(wishNum);
		   return false;
			}
		}

	//카테고리 선택 없는 전체 관심상품 리스트 
	@Override
	public List<ItemDto> selectAllWishItems(Integer storeCode) throws Exception {
			return shopDslRepo.selectAllWishItems(storeCode);
	}

	@Override
	public List<ItemDto> selectAllWishItemsByCategory(Integer storeCode, Integer majorNum, Integer middleNum,Integer subNum) throws Exception {
		
		return shopDslRepo.selectAllWishItemsByCategroy(storeCode, majorNum, middleNum, subNum);
	}

	//wishItem 에서 선택된 상품만 관심 상품 해제 
	@Override
	public Boolean deleteCheckedWishItem(Integer storeCode,List<Integer>wishItemNumList) throws Exception {
		if(shopDslRepo.deleteCheckedWishItem(storeCode,wishItemNumList)==wishItemNumList.size()) {
			return true;
		}else {
			return false;	
		}
	}

}
