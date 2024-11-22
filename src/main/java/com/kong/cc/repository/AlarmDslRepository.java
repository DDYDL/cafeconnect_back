package com.kong.cc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.QAlarm;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlarmDslRepository {
	
	private final JPAQueryFactory jpaQueryFactory;
	
	public List<Alarm> selectAlarmList(Integer storeCode) throws Exception {
		QAlarm alarm = QAlarm.alarm;
		
		return jpaQueryFactory.selectFrom(alarm)
				.where(alarm.storeAr.storeCode.eq(storeCode))
				.fetch();
	}
}
