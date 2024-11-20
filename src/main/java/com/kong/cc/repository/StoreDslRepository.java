package com.kong.cc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.kong.cc.entity.QStore;
import com.kong.cc.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class StoreDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public Long findStoreCount() throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.select(store.count())
				.from(store).fetchOne();
	}
	
	public List<Store> findStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.orderBy(store.storeCode.desc())
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
	
	public Long searchStoreCount(String type, String word) throws Exception {
		QStore store = QStore.store;
		Long cnt = 0L;
		if(type.equals("storeName")) {
			cnt = jpaQueryFactory.select(store.count())
				.from(store)
				.where(store.storeName.contains(word))
				.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word))
					.fetchOne();
		}
		return cnt;
	}	
	public List<Store> searchStoreListByPaging(PageRequest pageRequest, String type, String word) throws Exception {
		QStore store = QStore.store;
		List<Store> storeList = null;
		if(type.equals("storeName")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeName.contains(word))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		return storeList;
	}
}
