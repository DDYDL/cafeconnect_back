package com.kong.cc.service;

import java.util.List;

import com.kong.cc.dto.AlarmDto;

public interface AlarmService {
	public Integer registFcmToken(String username, String fcmToken) throws Exception;
	public List<AlarmDto> getAlarmList(Integer storeCode) throws Exception;
	public Boolean confirmAlarm(Integer alarmNum) throws Exception;
}
