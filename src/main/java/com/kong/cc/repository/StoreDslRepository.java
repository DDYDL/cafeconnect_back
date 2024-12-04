package com.kong.cc.repository;

import com.kong.cc.entity.QStore;
import com.kong.cc.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	// 가맹점 조회 페이지
	public Long findStoreCount() throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.select(store.count())
				.from(store).where(store.storeStatus.ne("inactive")).fetchOne();
	}
	
	public List<Store> findStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.where(store.storeStatus.ne("inactive"))
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
					  ,store.storeStatus.ne("inactive"))
				.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.ne("inactive"))
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
						  ,store.storeStatus.ne("inactive"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.ne("inactive"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		return storeList;
	}
	
	// 삭제 요청된 가맹점 조회 (storeStatus = "req")
	public Long findDeleteReqStoreCount() throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.select(store.count())
				.from(store).where(store.storeStatus.eq("req")).fetchOne();
	}
	
	public List<Store> findDeleteReqStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.where(store.storeStatus.eq("req"))
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
						  ,store.storeStatus.eq("req"))
					.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("req"))
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
						  ,store.storeStatus.eq("req"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("req"))
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
				.from(store).where(store.storeStatus.eq("inactive")).fetchOne();
	}
	
	public List<Store> findDeleteStoreListByPaging(PageRequest pageRequest) throws Exception {
		QStore store = QStore.store;
		return jpaQueryFactory.selectFrom(store)
				.where(store.storeStatus.eq("inactive"))
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
						  ,store.storeStatus.eq("inactive"))
					.fetchOne();
		} else if(type.equals("storeAddress")) {
			cnt= jpaQueryFactory.select(store.count())
					.from(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("inactive"))
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
						  ,store.storeStatus.eq("inactive"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		} else if(type.equals("storeAddress")) {
			storeList = jpaQueryFactory.selectFrom(store)
					.where(store.storeAddress.contains(word)
						  ,store.storeStatus.eq("inactive"))
					.orderBy(store.storeCode.desc())
					.offset(pageRequest.getOffset())
					.limit(pageRequest.getPageSize())
					.fetch();
		}
		return storeList;
	}
}
