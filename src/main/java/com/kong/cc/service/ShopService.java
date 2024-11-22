package com.kong.cc.service;

import java.util.List;

import com.kong.cc.dto.ItemDto;

public interface ShopService {

	List<ItemDto> selectItemsByCategroy(Integer majorNum,Integer middleNum,Integer subNum) throws Exception;
	List<ItemDto> selectItemsByKeyword(String Keyword) throws Exception;
	ItemDto selectItem(String itemCode) throws Exception;
	//Boolean checkIsWished (String itemCode,Integer storeCode)throws Exception;
	Integer checkIsWished (String itemCode,Integer storeCode)throws Exception;	
	Boolean toggleWishItem(String itemCode,Integer storeCode) throws Exception;
}
