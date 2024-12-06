package com.kong.cc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.dto.MemberDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.AlarmRepository;
import com.kong.cc.repository.MemberRepository;
import com.kong.cc.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
	
	private final AlarmRepository alarmRepository;
	private final AlarmDslRepository alarmDslRepository;
	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public List<AlarmDto> selectAlarmList(Integer storeCode) throws Exception {
		return alarmDslRepository.selectAlarmList(storeCode).stream().map(a->a.toDto()).collect(Collectors.toList());
	}

	@Override
	public String checkAlarmConfirm(Integer alarmNum) throws Exception {
		AlarmDto alarmDto = alarmRepository.findById(alarmNum).orElseThrow(()->new Exception("해당 알람 없음")).toDto();
		alarmDto.setAlarmStatus(true);
		alarmRepository.save(alarmDto.toEntity());
		return "true";
	}

	@Override
	public List<AlarmDto> selectAlarmType(Integer storeCode, String alarmType) throws Exception {
		return alarmDslRepository.selectAlarmByAlarmType(storeCode, alarmType).stream().map(a->a.toDto()).collect(Collectors.toList());
	}

	@Override
	public StoreDto selectStore(Integer storeCode) throws Exception {
		return alarmDslRepository.selectStoreByStoreCode(storeCode).toDto();
	}

	@Override
	public String updateStore(StoreDto storeDto) throws Exception {
		String str = null;
		StoreDto storeDtos = storeRepository.findById(storeDto.getStoreCode()).orElseThrow(()->new Exception("해당 가맹점 없음")).toDto();
		System.out.println(storeDtos);
		MemberDto memberDtos = memberRepository.findById(storeDtos.getMemberNum()).orElseThrow(()->new Exception("해당 사용자 없음")).toDto();
		System.out.println(memberDtos);
		
		storeDtos.setStorePhone(storeDto.getStorePhone());
		storeDtos.setStoreOpenTime(storeDto.getStoreOpenTime());
		storeDtos.setStoreCloseTime(storeDto.getStoreCloseTime());
		storeDtos.setStoreCloseDate(storeDto.getStoreCloseDate());
		
		storeDtos.setOwnerName(storeDto.getOwnerName());
		storeDtos.setOwnerPhone(storeDto.getOwnerPhone());
		storeDtos.setManagerName(storeDto.getManagerName());
		storeDtos.setManagerPhone(storeDto.getManagerPhone());
		
		memberDtos.setUsername(storeDto.getUsername());
		
		if(!storeDto.getPassword().equals("********")) { // 비밀번호 수정 시
			memberDtos.setPassword(bCryptPasswordEncoder.encode(storeDto.getPassword())); // 암호화 한 후 저장			
			str = "changePassword";
		} else {
			str = "notPassword";
		}
		
		storeRepository.save(storeDtos.toEntity());
		memberRepository.save(memberDtos.toEntity());
		return str;
	}

	@Override
	public List<StoreDto> selectStoreList(String username) throws Exception {
		return alarmDslRepository.selectStoreList(username).stream().map(s->s.toDto()).collect(Collectors.toList());
	}

	@Override
	public StoreDto addStore(Integer storeCode, String username) throws Exception {
		StoreDto storeDto = storeRepository.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점 없음")).toDto();
		MemberDto memberDto = memberRepository.findByUsername(username).orElseThrow(()->new Exception("해당 사용자 없음")).toDto();
		storeDto.setStoreStatus("active");
		storeDto.setMemberNum(memberDto.getMemberNum());
		System.out.println(storeDto);
		storeRepository.save(storeDto.toEntity());
		return storeDto;
	}

	@Override
	public String deleteStore(Integer storeCode) throws Exception {
		StoreDto storeDto = storeRepository.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점 없음")).toDto();
		storeDto.setStoreStatus("req");
		storeRepository.save(storeDto.toEntity());
		return "true";
	}

}
