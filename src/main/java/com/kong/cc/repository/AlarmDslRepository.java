package com.kong.cc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.QAlarm;
import com.kong.cc.entity.QStore;
import com.kong.cc.entity.Store;
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
	
	public List<Store> selectStoreList(Integer memberNum) throws Exception {
		QStore store = QStore.store;
		
		return jpaQueryFactory.selectFrom(store)
				.where(store.member.memberNum.eq(memberNum))
				.fetch();
	}
}
