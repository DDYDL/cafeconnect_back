package com.kong.cc.repository;

import com.kong.cc.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
	public List<Alarm> findByAlarmType(String alarmType) throws Exception;
	public List<Alarm> findByStoreAr_StoreCodeAndAlarmStatusFalse(Integer storeCode) throws Exception;
}
