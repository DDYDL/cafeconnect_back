package com.kong.cc.service;

import java.util.List;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.util.PageInfo;

public interface StoreManageService {
	List<StoreDto> storeList(PageInfo page, String type, String word, String status) throws Exception;
	StoreDto storeDetail(Integer storeCode) throws Exception;
	Integer createStoreCode() throws Exception; 
	Integer addStore(StoreDto storeDto) throws Exception;
	Integer deleteStore(Integer storeCode) throws Exception;
	Integer restoreStore(Integer storeCode) throws Exception;
}
