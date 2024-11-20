package com.kong.cc.repository;

import com.kong.cc.entity.Store;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ShopOrder;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {


    @Query("SELECT o FROM ShopOrder o WHERE o.orderDate = :orderDate AND o.storeO = :store")
    List<ShopOrder> findByOrderDateAndStoreO(@Param("orderDate") LocalDate orderDate, @Param("store") Store store);
}
