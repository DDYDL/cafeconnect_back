package com.kong.cc.repository;

import com.kong.cc.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	public List<Store> findByStoreNameContaining(String storeName) throws Exception;
	public List<Store> findByStoreStatus(String storeStatus) throws Exception;
	public Store findByStoreName(String storeName) throws Exception;
}
