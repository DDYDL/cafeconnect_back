package com.kong.cc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.Member;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.AlarmRepository;
import com.kong.cc.repository.AlarmRepository;
import com.kong.cc.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
	
	private final MemberRepository memberRepository;
	private final AlarmDslRepository alarmDslRepository;
	private final AlarmRepository alarmRepository;
	
	@Override
	public Integer registFcmToken(String username, String fcmToken) throws Exception {
		Optional<Member> member = memberRepository.findByUsername(username);
		if(member.isEmpty()) throw new Exception("사용자가 없습니다.");
		
		member.get().setFcmToken(fcmToken);
		memberRepository.save(member.get());
		return member.get().getStoreCode();
	}

	@Override
	public List<AlarmDto> getAlarmList(Integer storeCode) throws Exception {
		// alarmStatus가 false인 알람만 가져오기
		List<Alarm> alarmList = alarmRepository.findByStoreAr_StoreCodeAndAlarmStatusFalse(storeCode);
		System.out.println("alarmContent "+alarmList.get(0).getAlarmContent());
		
		return alarmList.stream().map(alarm->AlarmDto.builder()
						.alarmNum(alarm.getAlarmNum())
						.storeCode(storeCode)
						.alarmType(alarm.getAlarmType())
						.alarmContent(alarm.getAlarmContent())
						.alarmDate(alarm.getAlarmDate()).build()).collect(Collectors.toList());
	}

	@Override
	public Boolean confirmAlarm(Integer alarmNum) throws Exception {
		Optional<Alarm> alarm = alarmRepository.findById(alarmNum);
		if(alarm.isEmpty()) throw new Exception("알람이 없습니다.");
		
		alarm.get().setAlarmStatus(true);
		alarmRepository.save(alarm.get());
		return true;
	}

}
