package com.kong.cc.service;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.dto.StoreDto;

import java.util.List;

public interface MypageService {
	public List<AlarmDto> selectAlarmList(Integer storeCode) throws Exception;
	public String checkAlarmConfirm(Integer alarmNum) throws Exception;
	public List<AlarmDto> selectAlarmType(String alarmType) throws Exception;
	public StoreDto selectStore(Integer storeCode) throws Exception;
	public String updateStore(StoreDto storeDto) throws Exception;
	public List<StoreDto> selectStoreList(Integer memberNum) throws Exception;
	public String addStore(StoreDto storeDto) throws Exception;
	public String deleteStore(Integer storeCode) throws Exception;
}
