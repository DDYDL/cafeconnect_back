package com.kong.cc.repository;

import com.kong.cc.entity.Store;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ShopOrder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {


//    List<ShopOrder> findByStartDateAndEndDateAndStore(LocalDate startDate, LocalDate endDate, Integer storeCode);

//    @Query("SELECT o FROM ShopOrder o WHERE o.orderDate = :orderDate AND o.storeO = :store")
//    List<ShopOrder> findByOrderDateAndStore(@Param("orderDate") LocalDate orderDate, @Param("store") Integer store);
}
