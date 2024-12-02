package com.kong.cc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	public List<Store> findByStoreNameContaining(String storeName) throws Exception;
	public List<Store> findByStoreStatus(String storeStatus) throws Exception;
	public Store findByStoreName(String storeName) throws Exception;
	public Optional<Store> findByStoreCode(Integer storeCode) throws Exception;
}
