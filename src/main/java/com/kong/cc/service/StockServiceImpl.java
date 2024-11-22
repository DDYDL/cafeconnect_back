package com.kong.cc.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.dto.StockDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Stock;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.ItemRepository;
import com.kong.cc.repository.ShopOrderRepository;
import com.kong.cc.repository.StockDslRepository;
import com.kong.cc.repository.StockRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
	
	public final ShopOrderRepository shopOrderRepository;
	public final ItemRepository itemRepository;
	public final StoreRepository storeRepository;
	public final StockRepository stockRepository;
	public final StockDslRepository stockDslRepository; 
	
	@Override
	public List<ShopOrderDto> selectOrderList(Integer storeCode) throws Exception {
		return stockDslRepository.selectOrderList(storeCode).stream().map(s->s.toDto()).collect(Collectors.toList());
	}

	@Override
	public String addStockByOrderNum(List<Integer> orderNumList) throws Exception {
		for(Integer orderNum : orderNumList) {
			// ShopOrder에서 Stock으로 변경해서 추가
			// ShopOrder 찾기
			ShopOrderDto shopOrderDto = shopOrderRepository.findById(orderNum).get().toDto();
			
			// ShopOrder를 Stock으로 바꿔서 저장
			StockDto stockDto = new StockDto();
			stockDto.setStoreCode(shopOrderDto.getStoreCode());
			stockDto.setItemCode(shopOrderDto.getItemCode());
			stockDto.setStockReceiptDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.now()))); // 입고 날짜는 행이 들어가는 날짜로
			stockDto.setStockCount(shopOrderDto.getOrderCount());
			stockRepository.save(stockDto.toEntity());
			
			// ShopOrder orderState '입고완료'로 처리
			shopOrderDto.setOrderState("입고완료");
			shopOrderRepository.save(shopOrderDto.toEntity());
		}
		
		return "true";
	}

	@Override
	public List<StockDto> selectStockByStoreCode(Integer storeCode) throws Exception {
		return stockDslRepository.selectStockByStoreCode(storeCode).stream().map(s->s.toDto()).collect(Collectors.toList());
	}

	@Override
	public String addStock(StockDto stockDto) throws Exception {
		stockRepository.save(stockDto.toEntity());
		return "true";
	}

	@Override
	public String updateStock(StockDto stockDto) throws Exception {
		System.out.println("stockDto : " + stockDto);
		Stock stock = stockRepository.findById(stockDto.getStockNum()).get();
		System.out.println("stock : " + stock);
		// 유통기한 수정
		stock.setStockExpirationDate(stockDto.getStockExpirationDate());
		// 수량 수정
		stock.setStockCount(stockDto.getStockCount());
		stockRepository.save(stock);
		return "true";
	}

	@Override
	public String deleteStock(Integer stockNum) throws Exception {
		stockRepository.deleteById(stockNum);
		return "true";
	}

	@Override
	public List<StockDto> selectStockByCategory(Integer storeCode, Integer category, String expirationDate) throws Exception {
		return stockDslRepository.selectStockByCategory(storeCode, category, expirationDate).stream().map(s->s.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<StockDto> selectStockByKeyword(Integer storeCode, String keyword) throws Exception {
		// keyword가 들어있는 상품명 찾기
		return stockDslRepository.selectStockByKeyword(storeCode, keyword).stream().map(s->s.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<ItemDto> selectItemList() throws Exception {
		return itemRepository.findAll().stream().map(i->i.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<ItemDto> selectItemByName(String itemName) throws Exception {
		return itemRepository.findByItemNameContaining(itemName).stream().map(i->i.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<StoreDto> selectStockByItemCode(String itemCode) throws Exception {
		List<Tuple> tupleList = stockDslRepository.selectStockByItemCode(itemCode);
		List<StoreDto> storeDtoList = new ArrayList<>();
		for(Tuple tuple : tupleList) {
			StoreDto storeDto = tuple.get(0, Store.class).toDto();
			storeDto.setStockCount(tuple.get(1, Integer.class));
			System.out.println("========================");
			System.out.println(storeDto);
			
			storeDtoList.add(storeDto);
		}
		return storeDtoList;
	}
}
