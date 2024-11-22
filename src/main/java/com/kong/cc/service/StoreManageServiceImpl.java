package com.kong.cc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.StoreDslRepository;
import com.kong.cc.repository.StoreRepository;
import com.kong.cc.util.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreManageServiceImpl implements StoreManageService {

	private final StoreRepository storeRepository;
	private final StoreDslRepository storeDslRepository;
	
	@Override
	public List<StoreDto> storeList(PageInfo page, String type, String word, String status) throws Exception {
		PageRequest pageRequest = PageRequest.of(page.getCurPage()-1, 10);
		List<StoreDto> storeDtoList = null;
		Long allCnt = 0L;
		
		switch(status) {
			case "Req": 
				if(word==null || word.trim().equals("")) {
					storeDtoList = storeDslRepository.findDeleteReqStoreListByPaging(pageRequest).stream()
							.map(s->s.toDto()).collect(Collectors.toList());
					allCnt = storeDslRepository.findDeleteReqStoreCount();
				} else {
					storeDtoList = storeDslRepository.searchDeleteReqStoreListByPaging(pageRequest, type, word)
							.stream().map(s->s.toDto()).collect(Collectors.toList());
					allCnt = storeDslRepository.searchDeleteReqStoreCount(type, word);
				}
				
			case "Delete": 
				if(word==null || word.trim().equals("")) {
					storeDtoList = storeDslRepository.findDeleteStoreListByPaging(pageRequest).stream()
							.map(s->s.toDto()).collect(Collectors.toList());
					allCnt = storeDslRepository.findDeleteStoreCount();
				} else {
					storeDtoList = storeDslRepository.searchDeleteStoreListByPaging(pageRequest, type, word)
							.stream().map(s->s.toDto()).collect(Collectors.toList());
					allCnt = storeDslRepository.searchDeleteStoreCount(type, word);
				}
				
			default:
				if(word==null || word.trim().equals("")) {
					storeDtoList = storeDslRepository.findStoreListByPaging(pageRequest).stream()
							.map(s->s.toDto()).collect(Collectors.toList());
					allCnt = storeDslRepository.findStoreCount();
				} else {
					storeDtoList = storeDslRepository.searchStoreListByPaging(pageRequest, type, word)
							.stream().map(s->s.toDto()).collect(Collectors.toList());
					allCnt = storeDslRepository.searchStoreCount(type, word);
				}
		}
		Integer allPage = (int)(Math.ceil(allCnt.doubleValue()/pageRequest.getPageSize()));
		Integer startPage = (page.getCurPage()-1)/10*10+1;
		Integer endPage = Math.min(startPage+10-1, allPage);		
		
		page.setAllPage(allPage);
		page.setStartPage(startPage);
		page.setEndPage(endPage);
		return storeDtoList;
	}

	@Override
	public Integer addStore(StoreDto storeDto) throws Exception {
		Store store = storeDto.toEntity();
		storeRepository.save(store);		
		return store.getStoreCode();
	}

	@Override
	public Integer deleteStore(Integer storeCode) throws Exception {
		Store store = storeRepository.findById(storeCode).orElseThrow(()->new Exception("가맹점 코드 오류"));
		if(store!=null) {
			store.setStoreStatus("Delete");
		}
		storeRepository.save(store);		
		return store.getStoreCode();
	}

	@Override
	public Integer restoreStore(Integer storeCode) throws Exception {
		Store store = storeRepository.findById(storeCode).orElseThrow(()->new Exception("가맹점 코드 오류"));
		if(store!=null) {
			store.setStoreStatus("");
		}
		storeRepository.save(store);		
		return store.getStoreCode();
	}

	@Override
	public StoreDto storeDetail(Integer storeCode) throws Exception {
		Store store = storeRepository.findById(storeCode).orElseThrow(()->new Exception("가맹점 코드 오류"));
		return store.toDto();
	}

	@Override
	public Integer createStoreCode() throws Exception {
		Integer storeCode = storeDslRepository.selectLastStoreCode();
		return storeCode+1;
	}

}
