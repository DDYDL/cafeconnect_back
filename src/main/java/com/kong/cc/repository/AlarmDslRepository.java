package com.kong.cc.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.kong.cc.entity.Alarm;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.Notice;
import com.kong.cc.entity.QAlarm;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QMenu;
import com.kong.cc.entity.QNotice;
import com.kong.cc.entity.QStore;
import com.kong.cc.entity.Store;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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
				.orderBy(alarm.alarmDate.desc(), alarm.alarmNum.desc())
				.fetch();
	}
	
	public List<Store> selectStoreList(String username) throws Exception {
		QStore store = QStore.store;
		
		return jpaQueryFactory.selectFrom(store)
				.where(store.member.username.eq(username))
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

	public List<Notice> selectNoticeByNoticeTypeAndNoticeDate(String noticeType) throws Exception {
		QNotice notice = QNotice.notice;

		return jpaQueryFactory.selectFrom(notice)
				.where(notice.noticeType.eq(noticeType))
				.where(expirationDateSelect("true", notice.noticeDate))
				.fetch();
	}

	private BooleanExpression expirationDateSelect(String noticeDateBool, DatePath<Date> noticeDate) {
		// 현재 날짜 기준 expirationDate와 마이너스 연산을 한다.(하루 전 올라온 공지사항만 확인)
		NumberTemplate dateFormat = Expressions.numberTemplate(Integer.class, "DATEDIFF(curdate(), {0})", noticeDate);
		return StringUtils.hasText(noticeDateBool) ? dateFormat.loe(1) : null;
	}
	
	public List<Alarm> selectAlarmByAlarmType(Integer storeCode, String alarmType) throws Exception {
		QAlarm alarm = QAlarm.alarm;

		return jpaQueryFactory.selectFrom(alarm)
				.where(alarm.storeAr.storeCode.eq(storeCode))
				.where(alarm.alarmType.eq(alarmType))
				.fetch();
	}
}
