package com.kong.cc.service;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.dto.StoreDto;

import java.util.List;

public interface MypageService {
	public List<AlarmDto> selectAlarmList(Integer storeCode) throws Exception;
	public String checkAlarmConfirm(Integer alarmNum) throws Exception;
	public List<AlarmDto> selectAlarmType(Integer storeCode, String alarmType) throws Exception;
	public StoreDto selectStore(Integer storeCode) throws Exception;
	public String updateStore(StoreDto storeDto) throws Exception;
	public List<StoreDto> selectStoreList(String username) throws Exception;
	public StoreDto addStore(Integer storeCode, String username) throws Exception;
	public String deleteStore(Integer storeCode) throws Exception;
}
