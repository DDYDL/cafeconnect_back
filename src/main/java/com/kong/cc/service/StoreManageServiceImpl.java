package com.kong.cc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kong.cc.dto.StoreDto;
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
	public List<StoreDto> storeList(PageInfo page, String type, String word) throws Exception {
		PageRequest pageRequest = PageRequest.of(page.getCurPage()-1, 10);
		List<StoreDto> storeDtoList = null;
		Long allCnt = 0L;
		if(word==null || word.trim().equals("")) {
			storeDtoList = storeDslRepository.findStoreListByPaging(pageRequest).stream()
					.map(s->s.toDto()).collect(Collectors.toList());
			allCnt = storeDslRepository.findStoreCount();
		} else {
			storeDtoList = storeDslRepository.searchStoreListByPaging(pageRequest, type, word)
					.stream().map(s->s.toDto()).collect(Collectors.toList());
			allCnt = storeDslRepository.searchStoreCount(type, word);
		}
		
		Integer allPage = (int)(Math.ceil(allCnt.doubleValue()/pageRequest.getPageSize()));
		Integer startPage = (page.getCurPage()-1)/10*10+1;
		Integer endPage = Math.min(startPage+10-1, allPage);		
		
		page.setAllPage(allPage);
		page.setStartPage(startPage);
		page.setEndPage(endPage);
		return storeDtoList;
	}

}
