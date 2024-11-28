package com.kong.cc.repository;

import com.kong.cc.entity.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {


//    List<ShopOrder> findByStartDateAndEndDateAndStore(LocalDate startDate, LocalDate endDate, Integer storeCode);

//    @Query("SELECT o FROM ShopOrder o WHERE o.orderDate = :orderDate AND o.storeO = :store")
//    List<ShopOrder> findByOrderDateAndStore(@Param("orderDate") LocalDate orderDate, @Param("store") Integer store);
}
