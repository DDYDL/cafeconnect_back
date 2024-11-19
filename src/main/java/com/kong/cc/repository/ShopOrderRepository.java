package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ShopOrder;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {

}
