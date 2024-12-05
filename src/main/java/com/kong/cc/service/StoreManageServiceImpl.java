package com.kong.cc.service;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.StoreDslRepository;
import com.kong.cc.repository.StoreRepository;
import com.kong.cc.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
		
		boolean isWordEmpty = (word == null || word.trim().equals(""));

		if (isWordEmpty) {
		    switch (status) {
		        case "req": System.out.println("req service");
		            storeDtoList = storeDslRepository.findDeleteReqStoreListByPaging(pageRequest)
		                    .stream().map(s -> s.toDto()).collect(Collectors.toList());
		            allCnt = storeDslRepository.findDeleteReqStoreCount();
		            break;
		        case "delete": System.out.println("delete service");
		            storeDtoList = storeDslRepository.findDeleteStoreListByPaging(pageRequest)
		                    .stream().map(s -> s.toDto()).collect(Collectors.toList());
		            allCnt = storeDslRepository.findDeleteStoreCount();
		            break;
		        default: System.out.println("active/inactive service");
		            storeDtoList = storeDslRepository.findStoreListByPaging(pageRequest)
		                    .stream().map(s -> s.toDto()).collect(Collectors.toList());
		            allCnt = storeDslRepository.findStoreCount();
		            break;
		    }
		} else {
		    switch (status) {
		        case "req":
		            storeDtoList = storeDslRepository.searchDeleteReqStoreListByPaging(pageRequest, type, word)
		                    .stream().map(s -> s.toDto()).collect(Collectors.toList());
		            allCnt = storeDslRepository.searchDeleteReqStoreCount(type, word);
		            break;
		        case "delete":
		            storeDtoList = storeDslRepository.searchDeleteStoreListByPaging(pageRequest, type, word)
		                    .stream().map(s -> s.toDto()).collect(Collectors.toList());
		            allCnt = storeDslRepository.searchDeleteStoreCount(type, word);
		            break;
		        default:
		            storeDtoList = storeDslRepository.searchStoreListByPaging(pageRequest, type, word)
		                    .stream().map(s -> s.toDto()).collect(Collectors.toList());
		            allCnt = storeDslRepository.searchStoreCount(type, word);
		            break;
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
		storeDto.setStoreCode(createStoreCode(storeDto));
		storeDto.setStoreStatus("inactive");
		Store store = storeDto.toEntity();
		storeRepository.save(store);		
		return store.getStoreCode();
	}

	@Override
	public Integer modifyStore(StoreDto storeDto) throws Exception {
		Store oStore = storeRepository.findById(storeDto.getStoreCode()).get();
		Store mStore = storeDto.toEntity();
		
		if(!(mStore.getStoreName()==null)&&!(mStore.getStoreName().equals(oStore.getStoreName()))) {
			oStore.setStoreName(mStore.getStoreName());
		}
		if(!(mStore.getStoreAddress()==null)&&!(mStore.getStoreAddress().equals(oStore.getStoreAddress()))) {
			oStore.setStoreAddress(mStore.getStoreAddress());
		}
		if(!(mStore.getStoreAddressNum()==null)&&!(mStore.getStoreAddressNum().equals(oStore.getStoreAddressNum()))) {
			oStore.setStoreAddressNum(mStore.getStoreAddressNum());
		}
		if(!(mStore.getStorePhone()==null)&&!(mStore.getStorePhone().equals(oStore.getStorePhone()))) {
			oStore.setStorePhone(mStore.getStorePhone());
		}
		if(!(mStore.getStoreOpenTime()==null) && !(mStore.getStoreOpenTime().equals(oStore.getStoreOpenTime()))) {
			oStore.setStoreOpenTime(mStore.getStoreOpenTime());
		}
		if(!(mStore.getStoreCloseTime()==null)&&!(mStore.getStoreCloseTime().equals(oStore.getStoreCloseTime()))) {
			oStore.setStoreCloseTime(mStore.getStoreCloseTime());
		}
		if(!(mStore.getStoreCloseDate()==null)&&!(mStore.getStoreCloseDate().equals(oStore.getStoreCloseDate()))) {
			oStore.setStoreCloseDate(mStore.getStoreCloseDate());
		}
		if(!(mStore.getOwnerName()==null)&&!(mStore.getOwnerName().equals(oStore.getOwnerName()))) {
			oStore.setOwnerName(mStore.getOwnerName());
		}
		if(!(mStore.getOwnerPhone()==null)&&!(mStore.getOwnerPhone().equals(oStore.getOwnerPhone()))) {
			oStore.setOwnerPhone(mStore.getOwnerPhone());
		}
		if(!(mStore.getManagerName()==null)&&!(mStore.getManagerName().equals(oStore.getManagerName()))) {
			oStore.setManagerName(mStore.getManagerName());
		}
		if(!(!(mStore.getManagerPhone()==null)&&mStore.getManagerPhone().equals(oStore.getManagerPhone()))) {
			oStore.setManagerPhone(mStore.getManagerPhone());
		}
		if(!(mStore.getContractPeriodStart()==null)&&!(mStore.getContractPeriodStart().equals(oStore.getContractPeriodStart()))) {
			oStore.setContractPeriodStart(mStore.getContractPeriodStart());
		}
		if(!(mStore.getContractPeriodEnd()==null)&&!(mStore.getContractPeriodEnd().equals(oStore.getContractPeriodEnd()))) {
			oStore.setContractPeriodEnd(mStore.getContractPeriodEnd());
		}
		if(!(mStore.getContractDate()==null)&&!(mStore.getContractDate().equals(oStore.getContractDate()))) {
			oStore.setContractDate(mStore.getContractDate());
		}
		if(!(mStore.getOpeningDate()==null)&&!(mStore.getOpeningDate().equals(oStore.getOpeningDate()))) {
			oStore.setOpeningDate(mStore.getOpeningDate());
		}

		storeRepository.save(oStore);		
		return oStore.getStoreCode();
	}

	@Override
	public Integer deleteStore(Integer storeCode) throws Exception {
		Store store = storeRepository.findById(storeCode).orElseThrow(()->new Exception("가맹점 코드 오류"));
		if(store!=null) {
			store.setStoreStatus("delete");
		}
		storeRepository.save(store);		
		return store.getStoreCode();
	}

	@Override
	public Integer restoreStore(Integer storeCode) throws Exception {
		Store store = storeRepository.findById(storeCode).orElseThrow(()->new Exception("가맹점 코드 오류"));
		if(store!=null) {
			store.setStoreStatus("active");
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
	public Integer createStoreCode(StoreDto storeDto) throws Exception {
		String sStoreCode = "";
		String storeRegion = storeDto.getStoreRegion();
		String contractDate = storeDto.getContractDate();
		System.out.println(contractDate);
		Map<String,String> koRegion = new HashMap<>();
		koRegion.put("서울", "200");
		koRegion.put("인천", "320");
		koRegion.put("대전", "420");
		koRegion.put("부산", "510");
		koRegion.put("울산", "520");
		koRegion.put("대구", "530");
		koRegion.put("광주", "620");
		koRegion.put("제주특별자치도", "640");
		koRegion.put("경기", "310");
		koRegion.put("강원", "330");
		koRegion.put("충남", "410");
		koRegion.put("충북", "430");
		koRegion.put("경북", "540");
		koRegion.put("경남", "550");
		koRegion.put("전남", "610");
		koRegion.put("전북", "630");

		for(String key : koRegion.keySet()) {
			if(storeRegion==key) sStoreCode=koRegion.get(key); break;
		}
		System.out.println(sStoreCode);
		sStoreCode = sStoreCode + (String)contractDate.replace("-", "") + new Random().nextInt(100);
		
		Integer storeCode = Integer.parseInt(sStoreCode);
		return storeCode;
	}


}
