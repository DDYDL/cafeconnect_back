package com.kong.cc.service;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.StoreDslRepository;
import com.kong.cc.repository.StoreRepository;
import com.kong.cc.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalTime;

import java.sql.Time;
import java.text.SimpleDateFormat;
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
		page.setAllCnt(allCnt);
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
		
		oStore.setStoreName(mStore.getStoreName());
		oStore.setStoreAddress(mStore.getStoreAddress());
		oStore.setStoreAddressNum(mStore.getStoreAddressNum());
		oStore.setStorePhone(mStore.getStorePhone());
		oStore.setStoreOpenTime(mStore.getStoreOpenTime());
		oStore.setStoreCloseTime(mStore.getStoreCloseTime());
		oStore.setStoreCloseDate(mStore.getStoreCloseDate());
		oStore.setOwnerName(mStore.getOwnerName());
		oStore.setOwnerPhone(mStore.getOwnerPhone());
		oStore.setManagerName(mStore.getManagerName());
		oStore.setManagerPhone(mStore.getManagerPhone());
		oStore.setContractPeriodStart(mStore.getContractPeriodStart());
		oStore.setContractPeriodEnd(mStore.getContractPeriodEnd());
		oStore.setContractDate(mStore.getContractDate());
		oStore.setOpeningDate(mStore.getOpeningDate());

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
			if(store.getMember()!=null) store.setMember(null);
			else store.setStoreStatus("inactive");
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
		System.out.println("dtoregion: "+storeRegion);
		Map<String,String> koRegion = new HashMap<>();
		koRegion.put("서울", "20");
		koRegion.put("인천", "32");
		koRegion.put("대전", "42");
		koRegion.put("부산", "51");
		koRegion.put("울산", "52");
		koRegion.put("대구", "53");
		koRegion.put("광주", "62");
		koRegion.put("제주특별자치도", "64");
		koRegion.put("경기", "31");
		koRegion.put("강원특별자치도", "33");
		koRegion.put("충남", "41");
		koRegion.put("충북", "43");
		koRegion.put("경북", "54");
		koRegion.put("경남", "55");
		koRegion.put("전남", "61");
		koRegion.put("전북특별자치도", "63");

		for(String region : koRegion.keySet()) {
			System.out.println("배열 key: "+ region);
			if(storeRegion.equals(region)) {
				sStoreCode=koRegion.get(region);
				break;
			}
		}
		String subDate = ((((String)contractDate).replace("-", "")).substring(2)).substring(0,4);
		
		sStoreCode = sStoreCode + subDate + new Random().nextInt(100);
		
		Integer storeCode = Integer.parseInt(sStoreCode);
		
		while(storeRepository.findById(storeCode).isPresent()){
			storeCode = createStoreCode(storeDto);
		}
		
		return storeCode;
	}


}
