package com.kong.cc.service;

import java.util.List;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.dto.StockDto;
import com.kong.cc.dto.StoreDto;

public interface StockService {
	public List<ShopOrderDto> selectOrderList(Integer storeCode) throws Exception;
	public String addStockByOrderNum(List<Integer> orderNumList) throws Exception;
	public List<StockDto> selectStockByStoreCode(Integer storeCode) throws Exception;
	public String addStock(StockDto stockDto) throws Exception;
	public String updateStock(StockDto stockDto) throws Exception;
	public String deleteStock(Integer stockNum) throws Exception;
	public List<StockDto> selectStockByCategory(Integer storeCode, Integer categoryNum, String expirationDate) throws Exception;
	public List<StockDto> selectStockByKeyword(Integer storeCode, String keyword) throws Exception;
	public List<ItemDto> selectItemList() throws Exception;
	public List<ItemDto> selectItemByName(String itemName) throws Exception;
	public List<StoreDto> selectStoreByItemCode(String itemCode) throws Exception;
}
