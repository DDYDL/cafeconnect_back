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
	
	// 가맹점 조회 페이지
	public Long findStoreCount() throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.select(store.count())
				.from(store).where(store.storeStatus.ne("delete")).fetchOne();
	}
	
	public List<Store> findStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.where(store.storeStatus.ne("delete"))
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
				.where(store.storeName.contains(word)
					  ,store.storeStatus.ne("delete"))
				.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.ne("delete"))
					.fetchOne();
		}
		return cnt;
	}	
	public List<Store> searchStoreListByPaging(PageRequest pageRequest, String type, String word) throws Exception {
		QStore store = QStore.store;
		List<Store> storeList = null;
		if(type.equals("storeName")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeName.contains(word)
						  ,store.storeStatus.ne("delete"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.ne("delete"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		return storeList;
	}
	
	// 삭제 요청된 가맹점 조회 (storeStatus = "Req")
	public Long findDeleteReqStoreCount() throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.select(store.count())
				.from(store).where(store.storeStatus.eq("Req")).fetchOne();
	}
	
	public List<Store> findDeleteReqStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.where(store.storeStatus.eq("Req"))
				.orderBy(store.storeCode.desc())
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
	
	public Long searchDeleteReqStoreCount(String type, String word) throws Exception {
		QStore store = QStore.store;
		Long cnt = 0L;
		if(type.equals("storeName")) {
			cnt = jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeName.contains(word)
						  ,store.storeStatus.eq("Req"))
					.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("Req"))
					.fetchOne();
		}
		return cnt;
	}	
	public List<Store> searchDeleteReqStoreListByPaging(PageRequest pageRequest, String type, String word) throws Exception {
		QStore store = QStore.store;
		List<Store> storeList = null;
		if(type.equals("storeName")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeName.contains(word)
						  ,store.storeStatus.eq("Req"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("Req"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		return storeList;
	}
	
	// 삭제된 가맹점 조회 (storeStatus = "Delete")
	public Long findDeleteStoreCount() throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.select(store.count())
				.from(store).where(store.storeStatus.eq("Delete")).fetchOne();
	}
	
	public List<Store> findDeleteStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.where(store.storeStatus.eq("Delete"))
				.orderBy(store.storeCode.desc())
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
	
	public Long searchDeleteStoreCount(String type, String word) throws Exception {
		QStore store = QStore.store;
		Long cnt = 0L;
		if(type.equals("storeName")) {
			cnt = jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeName.contains(word)
						  ,store.storeStatus.eq("Delete"))
					.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("Delete"))
					.fetchOne();
		}
		return cnt;
	}	
	public List<Store> searchDeleteStoreListByPaging(PageRequest pageRequest, String type, String word) throws Exception {
		QStore store = QStore.store;
		List<Store> storeList = null;
		if(type.equals("storeName")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeName.contains(word)
						  ,store.storeStatus.eq("Delete"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("Delete"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		return storeList;
	}
	
	public Integer selectLastStoreCode() {
		QStore store = QStore.store;
		Integer lastStoreCode = jpaQueryFactory.select(store.storeCode)
												.orderBy(store.storeCode.desc())
												.limit(1)
												.from(store).fetchOne();
		return lastStoreCode;
	}
}
