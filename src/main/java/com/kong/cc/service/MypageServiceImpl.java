package com.kong.cc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.AlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
	
	private final AlarmRepository alarmRepository;
	private final AlarmDslRepository alarmDslRepository;
	
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
	public List<AlarmDto> selectAlarmType(String alarmType) throws Exception {
		return alarmRepository.findByAlarmType(alarmType).stream().map(a->a.toDto()).collect(Collectors.toList());
	}

}
