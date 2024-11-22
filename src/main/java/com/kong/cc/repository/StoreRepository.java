package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	public List<Store> findByStoreNameContaining(String storeName) throws Exception;
}
