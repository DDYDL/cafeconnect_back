package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
	public List<Alarm> findByAlarmType(String alarmType) throws Exception;
}
