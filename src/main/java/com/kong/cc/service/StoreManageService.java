package com.kong.cc.service;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.util.PageInfo;

import java.util.List;

public interface StoreManageService {
	List<StoreDto> storeList(PageInfo page, String type, String word, String status) throws Exception;
	StoreDto storeDetail(Integer storeCode) throws Exception;
	Integer createStoreCode() throws Exception; 
	Integer addStore(StoreDto storeDto) throws Exception;
	Integer modifyStore(StoreDto storeDto) throws Exception;
	Integer deleteStore(Integer storeCode) throws Exception;
	Integer restoreStore(Integer storeCode) throws Exception;
}
