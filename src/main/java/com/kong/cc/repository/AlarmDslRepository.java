package com.kong.cc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.QAlarm;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QMenu;
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
	
	public List<Alarm> selectAlarmListStatusFalse(Integer storeCode) throws Exception {
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
	
	public List<Menu> selectMenuByCategory(Integer menuCategoryNum) throws Exception {
		QMenu menu = QMenu.menu;
		
		return jpaQueryFactory.selectFrom(menu)
				.where(menu.menuCategory.menuCategoryNum.eq(menuCategoryNum))
				.fetch();
	}
	
	// 로그인 시 member의 store List 가져오기
	public List<Store> selectStoreByMemberNum(Integer memberNum) throws Exception {
		QStore store = QStore.store;
			
		return jpaQueryFactory.selectFrom(store)
				.where(store.member.memberNum.eq(memberNum))
				.fetch();
	}
		
	public Item selectItemByItemCode(String itemCode) throws Exception {
		QItem item = QItem.item;
		
		return jpaQueryFactory.selectFrom(item)
				.where(item.itemCode.eq(itemCode))
				.fetchOne();
	}
}
