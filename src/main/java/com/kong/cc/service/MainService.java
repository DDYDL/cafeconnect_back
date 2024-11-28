package com.kong.cc.service;

import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.MenuCategoryDto;
import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.StoreDto;

import java.util.List;

public interface MainService {
	public List<MenuDto> selectMenu() throws Exception;
	public List<StoreDto> selectStoreByStoreAddress(String address) throws Exception;
	public List<StoreDto> selectStoreByName(String storeName) throws Exception;
	public List<MenuCategoryDto> selectMenuCategory() throws Exception;
	public List<MenuDto> selectMenuByCategory(Integer categoryNum) throws Exception;
	public List<ComplainDto> complainList() throws Exception;
	public String complainWrite(ComplainDto complainDto) throws Exception;
}
