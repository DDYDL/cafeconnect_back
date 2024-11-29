package com.kong.cc.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.MenuCategoryDto;
import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.ComplainRepository;
import com.kong.cc.repository.MenuCategoryRepository;
import com.kong.cc.repository.MenuRepository;
import com.kong.cc.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
	
	private final MenuRepository menuRepository;
	private final MenuCategoryRepository menuCategoryRepository;
	private final StoreRepository storeRepository;
	private final ComplainRepository comlainRepository;
	private final AlarmDslRepository alarmDslRepository;

	@Override
	public List<MenuDto> selectMenu() throws Exception {
		return menuRepository.findByMenuStatusIsNotNull().stream().map(m->m.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<StoreDto> selectStoreByStoreAddress(String address) throws Exception {
		// 해당 위치 근처의 가맹점을 가져온다.
		return null;
	}

	@Override
	public List<StoreDto> selectStoreByName(String storeName) throws Exception {
		// 검색한 이름이 들어간 가맹점을 찾는다.
		return storeRepository.findByStoreNameContaining(storeName).stream().map(s->s.toDto()).collect(Collectors.toList());
	}
	
	@Override
	public List<MenuCategoryDto> selectMenuCategory() throws Exception {
		return menuCategoryRepository.findAll().stream().map(m->m.toDto()).collect(Collectors.toList());
	}
	
	@Override
	public List<MenuDto> selectMenuByCategory(Integer categoryNum) throws Exception {
		return alarmDslRepository.selectMenuByCategory(categoryNum).stream().map(m->m.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<ComplainDto> complainList() throws Exception {
		return comlainRepository.findAll(Sort.by(Sort.Direction.DESC, "complainDate")).stream().map(c->c.toDto()).collect(Collectors.toList());
	}

	@Override
	public String complainWrite(ComplainDto complainDto) throws Exception {
		complainDto.setStoreCode(storeRepository.findByStoreName(complainDto.getStoreName()).getStoreCode());
		complainDto.setComplainStatus(false);
		comlainRepository.save(complainDto.toEntity());
		return "true";
	}

	@Override
	public List<StoreDto> allStoreList() throws Exception {
		return storeRepository.findByStoreStatus("active").stream().map(s->s.toDto()).collect(Collectors.toList());
	}
}
