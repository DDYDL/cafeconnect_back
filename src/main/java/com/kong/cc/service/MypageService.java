package com.kong.cc.service;

import java.util.List;

import com.kong.cc.dto.AlarmDto;

public interface MypageService {
	public List<AlarmDto> selectAlarmList(Integer storeCode) throws Exception;
	public String checkAlarmConfirm(Integer alarmNum) throws Exception;
	public List<AlarmDto> selectAlarmType(String alarmType) throws Exception;
}
